package venus.assembler

import venus.linker.RelocationInfo
import venus.riscv.MemorySegments
import venus.riscv.Program

data class DebugInfo(val lineNo: Int, val line: String)
data class DebugInstruction(val debug: DebugInfo, val LineTokens: List<String>)

/**
 * This singleton implements a simple two-pass assembler to transform files into programs.
 */
object Assembler {
    /**
     * Assembles the given code into an unlinked Program.
     *
     * @param text the code to assemble.
     * @return an unlinked program.
     * @see venus.linker.Linker
     * @see venus.simulator.Simulator
     * @throws AssemblerError for invalid code inputs.
     */
    fun assemble(text: String): Program = AssemblerState(text).assemble()

    /** Stores the state of a single program assembly */
    internal class AssemblerState(val text: String) {
        /** The program we are currently assembling */
        internal val prog = Program()
        /** The text offset where the next instruction will be written */
        internal var currentTextOffset = MemorySegments.TEXT_BEGIN
        /** The data offset where more data will be written */
        internal var currentDataOffset = MemorySegments.STATIC_BEGIN
        /** Whether or not we are currently in the text segment */
        internal var inTextSegment = true
        /** TAL Instructions which will be added to the program */
        internal val talInstructions = ArrayList<DebugInstruction>()
        /** Mapping from labels to offsets from [passOne] */
        internal val symbolTable = HashMap<String, Int>()
        /** List of all labels in this file from [passOne] */
        internal val relocationTable = ArrayList<RelocationInfo>()
        /** The current line number (for user-friendly errors) */
        internal var currentLineNumber = 0

        /**
         * Runs both passes of the assembler.
         *
         * @return an unlinked program.
         * @see passOne
         * @see passTwo
         * @throws AssemblerError for invalid code inputs.
         */
        fun assemble(): Program {
            try {
                passOne()
            } catch (e: AssemblerError) {
                throw AssemblerError(currentLineNumber, e)
            }
            passTwo()
            return prog
        }

        /**
         * Pass #1 of our two pass assembler.
         *
         * It parses labels, expands pseudo-instructions and follows assembler directives.
         * It populations [talInstructions], which is then used by [passTwo] in order to actually assemble the code.
         */
        private fun passOne() {
            for (line in text.split('\n')) {
                currentLineNumber++

                val offset = getOffset()

                val (label, args) = Lexer.lexLine(line)
                if (label.isNotEmpty()) {
                    symbolTable.put(label, offset)
                }

                if (args.isEmpty() || args[0].isEmpty()) continue // empty line

                if (isAssemblerDirective(args[0])) {
                    parseAssemblerDirective(args[0], args.drop(1), line)
                } else {
                    val expandedInsts = replacePseudoInstructions(args)
                    for (inst in expandedInsts) {
                        val dbg = DebugInfo(currentLineNumber, line)
                        talInstructions.add(DebugInstruction(dbg, inst))
                        currentTextOffset += 4
                    }
                }
            }

            for ((label, offset) in symbolTable) {
                prog.addLabel(label, offset)
            }

            for ((label, offset) in relocationTable) {
                prog.addRelocation(label, offset - MemorySegments.TEXT_BEGIN)
            }
        }

        /**
         * Pass #2 of our two part assembler.
         *
         * It writes TAL instructions to the program, and also adds debug info to the program.
         * @see addInstruction
         * @see venus.riscv.Program.addDebugInfo
         */
        private fun passTwo() {
            for ((dbg, inst) in talInstructions) {
                try {
                    addInstruction(inst)
                    prog.addDebugInfo(dbg)
                } catch (e: AssemblerError) {
                    val (lineNumber, _) = dbg
                    throw AssemblerError(lineNumber, e)
                }
            }
        }

        /**
         * Adds machine code corresponding to our instruction to the program.
         *
         * @param tokens a list of strings corresponding to the space delimited line
         */
        private fun addInstruction(tokens: LineTokens) {
            if (tokens.isEmpty() || tokens[0].isEmpty()) return
            val cmd = getInstruction(tokens)
            val disp: WriterDispatcher = try {
                WriterDispatcher.valueOf(cmd)
            } catch (e: IllegalStateException) {
                throw AssemblerError("no such instruction $cmd")
            }
            disp.writer(prog, disp.iform, tokens.drop(1))
        }

        /**
         * Replaces any pseudoinstructions which occur in our program.
         *
         * @param tokens a list of strings corresponding to the space delimited line
         * @return the corresponding TAL instructions (possibly unchanged)
         */
        private fun replacePseudoInstructions(tokens: LineTokens): List<LineTokens> {
            try {
                val cmd = getInstruction(tokens)
                val pw = PseudoDispatcher.valueOf(cmd).pw
                return pw(tokens.drop(1), this)
            } catch (t: Throwable) {
                /* TODO: don't use throwable here */
                /* not a pseudoinstruction, or expansion failure */
                return listOf(tokens)
            }
        }

        /**
         * Changes the assembler state in response to directives
         *
         * @param directive the assembler directive, starting with a "."
         * @param args any arguments following the directive
         * @param line the original line (which is needed for some directives)
         */
        private fun parseAssemblerDirective(directive: String, args: LineTokens, line: String) {
            when (directive) {
                ".data" -> inTextSegment = false
                ".text" -> inTextSegment = true

                ".byte" -> {
                    for (arg in args) {
                        prog.addToData(arg.toByte())
                        currentDataOffset++
                    }
                }

                ".asciiz" -> {
                    val asciiString = Lexer.lexAsciizDirective(line) ?:
                            throw AssemblerError("expected a quoted string: $line")

                    for (c in asciiString) {
                        if (c.toInt() !in 0..127) {
                            throw AssemblerError("unexpected non-ascii character: $c")
                        }
                        prog.addToData(c.toByte())
                        currentDataOffset++
                    }

                    /* Add NUL terminator */
                    prog.addToData(0)
                    currentDataOffset++
                }

                ".word" -> {
                    for (arg in args) {
                        val word = arg.toInt()
                        prog.addToData(word.toByte())
                        prog.addToData((word shr 8).toByte())
                        prog.addToData((word shr 16).toByte())
                        prog.addToData((word shr 24).toByte())
                        currentDataOffset += 4
                    }
                }

                else -> throw AssemblerError("unknown assembler directive $directive")
            }
        }

        /** Gets the current offset (either text or data) depending on where we are writing */
        private fun getOffset() = if (inTextSegment) currentTextOffset else currentDataOffset

        /**
         * Determines if the given token is an assembler directive
         *
         * @param cmd the token to check
         * @return true if the token is an assembler directive
         * @see parseAssemblerDirective
         */
        private fun isAssemblerDirective(cmd: String) = cmd.startsWith(".")

        /**
         * Gets the instruction from a line of code
         *
         * @param tokens the tokens from the current line
         * @return the instruction (aka the first argument, in lowercase)
         */
        private fun getInstruction(tokens: LineTokens) = tokens[0].toLowerCase()
    }
    /* TODO: add actual parser */
}
