package venus.assembler.pseudos

import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength
import venus.assembler.LineTokens
import venus.assembler.Assembler.AssemblerState

object MV : PseudoWriter() {
    internal override operator fun invoke(args: LineTokens,
        state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 2)
        return listOf(listOf("addi", args[0], args[1], "0"))
    }
}