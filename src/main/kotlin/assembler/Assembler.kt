package venus.assembler

import venus.riscv.InstructionFormat

class Assembler() {
    val prog = Program()

    fun addInstruction(tokens: List<String>): String {
        if (tokens.size < 1) return "no instrucion found"
        val cmd = tokens[0].toLowerCase()
        val iform: InstructionFormat
        val writer: InstructionWriter
        try {
            val disp: WriterDispatcher = WriterDispatcher.valueOf(cmd)
            iform = disp.iform
            writer = disp.writer
        } catch (e: IllegalArgumentException) {
            return "could not find the given instruction"
        }
        return writer(prog, iform, tokens.subList(1, tokens.size))
    }

    /* TODO: add actual parser */
}