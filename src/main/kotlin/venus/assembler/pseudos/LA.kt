package venus.assembler.pseudos

import venus.assembler.Assembler.AssemblerState
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength

/**
 * Writes pseudoinstruction `la reg, label`.
 *
 * Uses a `auipc` / `addi` pair and adds them to the relocation table
 */
object LA : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 3)

        val auipc = listOf("auipc", args[1], "0")
        state.prog.addRelocation(args[2], state.currentTextOffset)

        val addi = listOf("addi", args[1], args[1], "0")
        state.prog.addRelocation(args[2], state.currentTextOffset + 4) /* TODO: variable instruction length? */

        return listOf(auipc, addi)
    }
}