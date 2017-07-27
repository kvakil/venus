package venus.assembler.pseudos

import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength
import venus.assembler.LineTokens
import venus.assembler.Assembler.AssemblerState

/**
 * Writes pseudoinstruction `la reg, label`.
 *
 * Uses a `auipc` / `addi` pair and adds them to the relocation table
 */
object LA : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 2)

        val auipc = listOf("auipc", args[0], "0")
        state.prog.addRelocation(args[1], state.currentTextOffset)

        val addi = listOf("addi", args[0], args[0], "0")
        state.prog.addRelocation(args[1], state.currentTextOffset + 4)

        return listOf(auipc, addi)
    }
}