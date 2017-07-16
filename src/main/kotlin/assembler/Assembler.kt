package venus.assembler

import venus.riscv.MemorySegments
import venus.riscv.Program
import venus.linker.RelocationInfo

typealias LineTokens = List<String>
typealias Line = Pair<Int, LineTokens>

object Assembler {
    fun assemble(text: String): Program {
        return AssemblerState(text).assemble()
    }

    internal class AssemblerState(val text: String) {
        internal val prog = Program()
        internal var currentTextOffset = MemorySegments.TEXT_BEGIN
        internal var currentDataOffset = MemorySegments.STATIC_BEGIN
        internal var inTextSegment = true
        internal val TALInstructions = ArrayList<Line>()
        internal val symbolTable = HashMap<String, Int>()
        internal val relocationTable = ArrayList<RelocationInfo>()
        internal var currentLineNumber = 0

        fun assemble(): Program {
            try {
                passOne()
            } catch (e: AssemblerError) {
                throw AssemblerError(currentLineNumber, e)
            }
            passTwo()
            return prog
        }

        private fun passOne() {
            for (line in text.split('\n')) {
                currentLineNumber++

                val offset = getOffset()

                val (label, args) = Lexer.lexLine(line)
                if (label != "") {
                    symbolTable.put(label, offset)
                }

                if (args.size == 0 || args[0] == "") continue; // empty line

                if (isAssemblerDirective(args[0])) {
                    parseAssemblerDirective(args, line)
                } else {
                    val expandedInsts = replacePseudoInstructions(args)
                    for (inst in expandedInsts) {
                        TALInstructions.add(Pair(currentLineNumber, inst))
                        currentTextOffset += inst.size
                    }
                }
            }

            for ((label, offset) in symbolTable) {
                prog.addLabel(label, offset)
            }

            for ((label, offset) in relocationTable) {
                prog.addRelocation(label, offset)
            }
        }

        private fun passTwo() {
            for ((lineNumber, inst) in TALInstructions) {
                try {
                    addInstruction(inst)
                } catch (e: AssemblerError) {
                    throw AssemblerError(lineNumber, e)
                }
            }
        }

        private fun addInstruction(tokens: LineTokens) {
            if (tokens.size < 1 || tokens[0] == "") return
            val cmd = getInstruction(tokens)
            val disp: WriterDispatcher = try {
                WriterDispatcher.valueOf(cmd)
            } catch (e: IllegalStateException) {
                throw AssemblerError("no such instruction ${cmd}")
            }
            disp.writer(prog, disp.iform, tokens.drop(1))
        }

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

        private fun parseAssemblerDirective(args: LineTokens, line: String) {
            val directive = args[0]
            when (directive) {
                ".data" -> inTextSegment = false
                ".text" -> inTextSegment = true

                ".byte" -> {
                    for (arg in args.drop(1)) {
                        prog.addToData(arg.toByte())
                        currentDataOffset++
                    }
                }

                ".asciiz" -> {
                    val asciiString = Lexer.lexAsciizDirective(line)

                    if (asciiString == null) {
                        throw AssemblerError("expected a quoted string: ${line}")
                    }

                    for (c in asciiString) {
                        if (c.toInt() !in 0..127) {
                            throw AssemblerError("unexpected non-ascii character: ${c}")
                        }
                        prog.addToData(c.toByte())
                        currentDataOffset++
                    }

                    prog.addToData(0)
                    currentDataOffset++
                }

                ".word" -> {
                    for (arg in args.drop(1)) {
                        val word = arg.toInt()
                        prog.addToData(word.toByte())
                        prog.addToData((word shr 8).toByte())
                        prog.addToData((word shr 16).toByte())
                        prog.addToData((word shr 24).toByte())
                        currentDataOffset += 4
                    }
                }

                else -> throw AssemblerError("unknown assembler directive ${directive}")
            }
        }

        private fun getOffset() = if (inTextSegment) currentTextOffset else currentDataOffset
        private fun isAssemblerDirective(cmd: String) = cmd.startsWith(".")
        private fun getInstruction(tokens: LineTokens) = tokens[0].toLowerCase()
    }
    /* TODO: add actual parser */
}