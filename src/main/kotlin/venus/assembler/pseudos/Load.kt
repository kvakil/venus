package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter

/**
 * Writes a load pseudoinstruction. (Those applied to a label)
 */
object Load : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 3)

        val auipc = listOf("auipc", args[1], "0")
        state.addRelocation(args[2], state.getOffset())

        val load = listOf(args[0], args[1], "0", args[1])
        state.addRelocation(args[2], state.getOffset() + 4) /* TODO: variable instruction length? */

        return listOf(auipc, load)
    }
}