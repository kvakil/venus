package venus.assembler

import venus.riscv.MemorySegments

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
        disp.writer(prog, disp.iform, tokens.subList(1, tokens.size))
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
                    prog.addToData(args.subList(1, args.size).map { it.toByte() })
                    dataSize += 1
                }
            } else {
                val expandedInsts = replacePseudoInstructions(args)
                instructions.addAll(expandedInsts)
                textSize += 4 * expandedInsts.size
            }
        }
        return instructions
    }

    private fun isAssemblerDirective(cmd: String) = cmd.startsWith(".")

    fun assemble(text: String): Program {
        val prog = Program()
        val instructions = passOne(prog, text)
        instructions.forEach { addInstruction(prog, it) }
        return prog
    }

    /* TODO: add actual parser */
}