package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter
import venus.riscv.insts.dsl.relocators.PCRelHiRelocator
import venus.riscv.insts.dsl.relocators.PCRelLoRelocator

/**
 * Writes a store pseudoinstruction. (Those applied to a label)
 */
object Store : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 4)

        val auipc = listOf("auipc", args[3], "0")
        state.addRelocation(PCRelHiRelocator, state.getOffset(), args[2])

        val store = listOf(args[0], args[1], "0", args[3])
        state.addRelocation(PCRelLoRelocator, state.getOffset() + 4, args[2])

        return listOf(auipc, store)
    }
}
