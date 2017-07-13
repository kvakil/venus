package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.Program
import venus.riscv.Instruction

object DoNothingWriter : InstructionWriter() {
    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        prog.add(inst)
    }
}