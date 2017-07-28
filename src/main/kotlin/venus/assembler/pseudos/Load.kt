package venus.assembler.pseudos

import venus.assembler.Assembler.AssemblerState
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength

/**
 * Writes a load pseudoinstruction. (Those applied to a label)
 */
object Load : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 3)

        val auipc = listOf("auipc", args[1], "0")
        state.prog.addRelocation(args[2], state.currentTextOffset)

        val load = listOf(args[0], args[1], "0", args[1])
        state.prog.addRelocation(args[2], state.currentTextOffset + 4) /* TODO: variable instruction length? */

        return listOf(auipc, load)
    }
}