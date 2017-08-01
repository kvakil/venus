package venus.assembler.pseudos

import venus.assembler.Assembler.AssemblerState
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter

/** Writes pseudoinstruction `jal label` */
object JAL : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 2)
        return listOf(listOf("jal", "x1", args[1]))
    }
}