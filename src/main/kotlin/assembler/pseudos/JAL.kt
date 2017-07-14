package venus.assembler.pseudos

import venus.assembler.PseudoWriter
import venus.assembler.LineTokens
import venus.assembler.Assembler.AssemblerState

object JAL : PseudoWriter() {
    internal override operator fun invoke(args: LineTokens,
        state: AssemblerState): List<LineTokens> {
        if (args.size != 2) return listOf(args) // not the pseudo jal
        return listOf(listOf("jal", "x1", args[1]))
    }
}