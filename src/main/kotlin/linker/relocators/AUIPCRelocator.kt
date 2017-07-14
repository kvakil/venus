package venus.linker.relocators

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.linker.Relocator

object AUIPCRelocator : Relocator() {
    override operator fun invoke(inst: Instruction, pc: Int, target: Int) {
        val imm = (target - pc) shr 12
        inst.setField(InstructionField.IMM_31_12, imm)
    }
}