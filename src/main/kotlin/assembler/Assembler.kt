package venus.assembler

typealias LineTokens = List<String>

object Assembler {
    fun addInstruction(prog: Program, tokens: LineTokens) {
        if (tokens.size < 1) return
        val cmd = tokens[0].toLowerCase()
        val disp: WriterDispatcher = try {
            WriterDispatcher.valueOf(cmd)
        } catch (e: IllegalStateException) {
            throw AssemblerError("no such instruction")
        }
        disp.writer(prog, disp.iform, tokens.subList(1, tokens.size))
    }

    private fun passOne(prog: Program, text: String): List<LineTokens> {
        var offset = 0
        val instructions = ArrayList<LineTokens>()
        for (line in text.split('\n')) {
            val (label, args) = Lexer.lexLine(line)
            if (label != "")
                prog.addLabel(label, offset)
            /* TODO: add pseudoinstruction support here */
            /* TODO: abstract byte offset to InstructionFormat */
            if (args.size >= 1) {
                instructions.add(args)
                offset += 4
            }
        }
        return instructions
    }

    fun assemble(text: String): Program {
        val prog = Program()
        val instructions = passOne(prog, text)
        instructions.forEach({ addInstruction(prog, it) })
        return prog
    }

    /* TODO: add actual parser */
}