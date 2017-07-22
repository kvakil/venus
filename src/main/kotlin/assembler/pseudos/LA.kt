package venus.assembler.pseudos

import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength
import venus.assembler.LineTokens
import venus.assembler.Assembler.AssemblerState
import venus.linker.RelocationInfo

/**
 * Writes pseudoinstruction `la reg, label`.
 *
 * Uses a `auipc` / `addi` pair and adds them to the relocation table
 */
object LA : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 2)

        val auipc = listOf("auipc", args[0], "0")
        val auipcRelocation = RelocationInfo(args[1], state.currentTextOffset)
        state.relocationTable.add(auipcRelocation)

        val addi = listOf("addi", args[0], args[0], "0")
        val addiRelocation = RelocationInfo(args[1], state.currentTextOffset + 4)
        state.relocationTable.add(addiRelocation)

        return listOf(auipc, addi)
    }
}