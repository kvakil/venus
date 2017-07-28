package venus.assembler.pseudos

import venus.assembler.Assembler.AssemblerState
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength

/**
 * Writes pseudoinstruction `sge[u]`.
 * @todo add a settings option for "extended pseudoinstructions"
 */
object SGE : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 4)
        val unsigned = if (args[0].endsWith("u")) "u" else ""
        return listOf(listOf("slt$unsigned", args[1], args[3], args[2]))
    }
}
