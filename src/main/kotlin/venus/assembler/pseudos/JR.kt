package venus.assembler.pseudos

import venus.assembler.Assembler.AssemblerState
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter

/** Writes pseudoinstruction `jr register` */
object JR : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 2)
        return listOf(listOf("jalr", "x0", args[1], "0"))
    }
}