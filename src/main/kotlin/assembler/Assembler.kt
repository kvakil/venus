package venus.assembler

import venus.riscv.MemorySegments
import venus.riscv.Program

typealias LineTokens = List<String>

object Assembler {
    /* TODO: refactor Assembler from singleton pattern
     * this will let us more easily use state for directives */
    fun addInstruction(prog: Program, tokens: LineTokens) {
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

    private fun passOne(prog: Program, text: String): List<LineTokens> {
        val instructions = ArrayList<LineTokens>()
        var inTextSegment = true
        var textSize = 0
        var dataSize = 0
        for (line in text.split('\n')) {
            val offset = if (inTextSegment) textSize else dataSize
            val start = if (inTextSegment) MemorySegments.TEXT_BEGIN else MemorySegments.STATIC_BEGIN

            val (label, args) = Lexer.lexLine(line)
            if (label != "") {
                prog.addLabel(label, start + offset)
            }
            /* TODO: abstract byte offset to InstructionFormat */

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
                        dataSize++
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
                        dataSize++
                    }

                    prog.addToData(0)
                    dataSize++
                }
            } else {
                val expandedInsts = replacePseudoInstructions(args)
                instructions.addAll(expandedInsts)
                textSize += 4 * expandedInsts.size
            }
        }
        return instructions
    }

    private fun passTwo(prog: Program, instructions: List<LineTokens>) {
        for (inst in instructions) {
            addInstruction(prog, inst)
        }
    }

    private fun isAssemblerDirective(cmd: String) = cmd.startsWith(".")

    fun assemble(text: String): Program {
        val prog = Program()
        val instructions = passOne(prog, text)
        passTwo(prog, instructions)
        return prog
    }

    /* TODO: add actual parser */
}