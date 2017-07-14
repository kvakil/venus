package venus.assembler.pseudos

import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength
import venus.assembler.LineTokens
import venus.assembler.Assembler.AssemblerState
import venus.linker.RelocationInfo

object LA : PseudoWriter() {
    internal override operator fun invoke(args: LineTokens,
        state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 3)
        
        val auipc = listOf("auipc", args[1], "0")
        val auipcRelocation = RelocationInfo(args[2], state.textOffset)
        state.relocationTable.add(auipcRelocation)

        val addi = listOf("addi", args[1], args[1], "0")
        val addiRelocation = RelocationInfo(args[2], state.textOffset + 4)
        state.relocationTable.add(addiRelocation)

        return listOf(auipc, addi)
    }
}