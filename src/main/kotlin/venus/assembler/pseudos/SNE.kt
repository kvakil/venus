package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter

/**
 * Writes pseudoinstruction `sne` (set not equal to)
 * @todo add a settings option for "extended pseudoinstructions"
 */
object SNE : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 4)
        val subtract = listOf("sub", args[1], args[2], args[3])
        val checkNonZero = listOf("sltu", args[1], "x0", args[1])
        return listOf(subtract, checkNonZero)
    }
}
