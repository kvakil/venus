package venus.assembler

typealias LineTokens = List<String>

object Assembler {
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

    fun replacePseudoInstructions(tokens: LineTokens): List<LineTokens> {
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
        var offset = 0
        val instructions = ArrayList<LineTokens>()
        for (line in text.split('\n')) {
            val (label, args) = Lexer.lexLine(line)
            if (label != "")
                prog.addLabel(label, offset)
            /* TODO: abstract byte offset to InstructionFormat */

            if (args.size >= 1 && args[0] != "") {
                val expandedInsts = replacePseudoInstructions(args)
                instructions.addAll(expandedInsts)
                offset += 4 * expandedInsts.size
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