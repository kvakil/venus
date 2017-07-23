package venus.linker.relocators

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.linker.Relocator

object JALRelocator : Relocator() {
    override operator fun invoke(inst: Instruction, pc: Int, target: Int) {
        val imm = (target - pc) shr 1
        val imm_20 = imm shr 20
        val imm_10_1 = imm shr 1
        val imm_19_12 = imm shr 12
        val imm_11 = imm shr 20
        inst.setField(InstructionField.IMM_20, imm_20)
        inst.setField(InstructionField.IMM_10_1, imm_10_1)
        inst.setField(InstructionField.IMM_19_12, imm_19_12)
        inst.setField(InstructionField.IMM_11_J, imm_11)
    }
}