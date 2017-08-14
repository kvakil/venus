package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter

/** Writes pseudoinstruction `mv rd, rs1` */
object MV : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 3)
        return listOf(listOf("addi", args[1], args[2], "0"))
    }
}
