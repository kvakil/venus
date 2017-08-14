package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter
import venus.riscv.insts.dsl.relocators.PCRelHiRelocator
import venus.riscv.insts.dsl.relocators.PCRelLoRelocator

/**
 * Writes pseudoinstruction `call label`.
 */
object CALL : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 2)

        val auipc = listOf("auipc", "x6", "0")
        state.addRelocation(PCRelHiRelocator, state.getOffset(), args[1])

        val jalr = listOf("jalr", "x1", "x6", "0")
        state.addRelocation(PCRelLoRelocator, state.getOffset() + 4, args[1])

        return listOf(auipc, jalr)
    }
}
