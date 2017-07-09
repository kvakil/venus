package venus.assembler

class Assembler() {
    val prog = Program()

    fun addInstruction(tokens: List<String>) {
        if (tokens.size < 1) throw AssemblerError("no instruction found")
        val cmd = tokens[0].toLowerCase()
        val disp: WriterDispatcher = try {
            WriterDispatcher.valueOf(cmd)
        } catch (e: IllegalStateException) {
            throw AssemblerError("no such instruction")
        }
        disp.writer(prog, disp.iform, tokens.subList(1, tokens.size))
    }

    /* TODO: add actual parser */
}