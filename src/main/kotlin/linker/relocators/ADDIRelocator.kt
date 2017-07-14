package venus.linker.relocators

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.linker.Relocator

object ADDIRelocator : Relocator() {
    override operator fun invoke(inst: Instruction, pc: Int, target: Int) {
        val imm = target - (pc - 4)
        inst.setField(InstructionField.IMM_11_0, imm)
    }
}