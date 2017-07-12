package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.Program
import venus.riscv.Instruction

object ECALLWriter : InstructionWriter() {
    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        /* do nothing */
    }
}