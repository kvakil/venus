package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter

/**
 * Writes pseudoinstruction `sge` (set greater than or equal to)
 * @todo add a settings option for "extended pseudoinstructions"
 */
object SGE : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 4)
        val unsigned = if (args[0].endsWith("u")) "u" else ""
        val set = listOf("slt$unsigned", args[1], args[2], args[3])
        val invert = listOf("xori", args[1], args[1], "1")
        return listOf(set, invert)
    }
}
