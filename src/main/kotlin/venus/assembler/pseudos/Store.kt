package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter
import venus.riscv.insts.dsl.relocators.PCRelHiRelocator
import venus.riscv.insts.dsl.relocators.PCRelLoRelocator
import venus.riscv.userStringToInt

/**
 * Writes a store pseudoinstruction. (Those applied to a label)
 */
object Store : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 4)
        val label = args[2]
        try {
            userStringToInt(label)
            /* if it's a number, this is just an ordinary store instruction */
            return listOf(args)
        } catch (e: NumberFormatException) {
            /* assume it's a label */
        }

        val auipc = listOf("auipc", args[3], "0")
        state.addRelocation(PCRelHiRelocator, state.getOffset(), label)

        val store = listOf(args[0], args[1], "0", args[3])
        state.addRelocation(PCRelLoRelocator, state.getOffset() + 4, label)

        return listOf(auipc, store)
    }
}
