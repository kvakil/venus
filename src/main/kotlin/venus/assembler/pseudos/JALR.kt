package venus.assembler.pseudos

import venus.assembler.Assembler.AssemblerState
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter

/** Writes pseudoinstruction `jalr reg` */
object JALR : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 2)
        return listOf(listOf("jalr", "x1", args[1], "0"))
    }
}