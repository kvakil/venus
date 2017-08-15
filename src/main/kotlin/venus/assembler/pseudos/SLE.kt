package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter

/**
 * Writes pseudoinstruction `sle` (set less than or equal to)
 * @todo add a settings option for "extended pseudoinstructions"
 */
object SLE : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 4)
        checkStrictMode()
        val unsigned = if (args[0].endsWith("u")) "u" else ""
        val set = listOf("slt$unsigned", args[1], args[3], args[2])
        val invert = listOf("xori", args[1], args[1], "1")
        return listOf(set, invert)
    }
}
