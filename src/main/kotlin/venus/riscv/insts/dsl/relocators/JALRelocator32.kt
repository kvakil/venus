package venus.riscv.insts.dsl.relocators

import venus.riscv.InstructionField
import venus.riscv.MachineCode

object JALRelocator32 : InstructionRelocator32 {
    override operator fun invoke(mcode: MachineCode, pc: Int, target: Int) {
        val imm = (target - pc) shr 1
        mcode[InstructionField.IMM_20] = imm shr 20
        mcode[InstructionField.IMM_10_1] = imm shr 1
        mcode[InstructionField.IMM_19_12] = imm shr 12
        mcode[InstructionField.IMM_11_J] = imm shr 11
    }
}
