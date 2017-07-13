package venus.assembler

import venus.riscv.MemorySegments
import venus.riscv.Program

typealias LineTokens = List<String>

object Assembler {
    fun assemble(text: String): Program {
        return AssemblyFile(text).prog
    }

    private class AssemblyFile(val text: String) {
        val prog = Program()
        private var currentTextOffset = MemorySegments.TEXT_BEGIN
        private var currentDataOffset = MemorySegments.STATIC_BEGIN
        private var inTextSegment = true
        private val TALInstructions = ArrayList<LineTokens>()

        /* TODO: refactor Assembler from singleton pattern
        * this will let us more easily use state for directives */
        fun addInstruction(tokens: LineTokens) {
            if (tokens.size < 1 || tokens[0] == "") return
            val cmd = tokens[0].toLowerCase()
            val disp: WriterDispatcher = try {
                WriterDispatcher.valueOf(cmd)
            } catch (e: IllegalStateException) {
                throw AssemblerError("no such instruction ${cmd}")
            }
            disp.writer(prog, disp.iform, tokens.drop(1))
        }

        private fun replacePseudoInstructions(tokens: LineTokens): List<LineTokens> {
            try {
                val cmd = tokens[0].toLowerCase()
                val pw = PseudoDispatcher.valueOf(cmd).pw
                return pw(tokens)
            } catch (t: Throwable) {
                /* TODO: don't use throwable here */
                /* not a pseudoinstruction, or expansion failure */
                return listOf(tokens)
            }
        }

        private fun getOffset() = if (inTextSegment) currentTextOffset else currentDataOffset

        private fun passOne() {
            for (line in text.split('\n')) {
                val offset = getOffset()

                val (label, args) = Lexer.lexLine(line)
                if (label != "") {
                    prog.addLabel(label, offset)
                }

                if (args.size == 0 || args[0] == "") continue; // empty line

                /* TODO: change this; completely. */
                if (isAssemblerDirective(args[0])) {
                    if (args[0] == ".data") {
                        inTextSegment = false
                    } else if (args[0] == ".text") {
                        inTextSegment = true
                    } else if (args[0] == ".byte") {
                        for (arg in args.drop(1)) {
                            prog.addToData(arg.toByte())
                            currentDataOffset++
                        }
                    } else if (args[0] == ".asciiz") {
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
                } else {
                    val expandedInsts = replacePseudoInstructions(args)
                    TALInstructions.addAll(expandedInsts)
                    currentTextOffset += 4 * expandedInsts.size
                }
            }
        }

        private fun passTwo() {
            for (inst in TALInstructions) {
                addInstruction(inst)
            }
        }

        private fun isAssemblerDirective(cmd: String) = cmd.startsWith(".")

        fun assemble(): Program {
            passOne()
            passTwo()
            return prog
        }
    }
    /* TODO: add actual parser */
}