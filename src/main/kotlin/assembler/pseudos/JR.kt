package venus.assembler.pseudos

import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength
import venus.assembler.LineTokens
import venus.assembler.Assembler.AssemblerState

object JR : PseudoWriter() {
    internal override operator fun invoke(args: LineTokens,
        state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 1)
        return listOf(listOf("jalr", "x0", args[0], "0"))
    }
}