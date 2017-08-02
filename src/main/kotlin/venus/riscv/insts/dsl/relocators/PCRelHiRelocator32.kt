package venus.riscv.insts.dsl.relocators

import venus.riscv.InstructionField
import venus.riscv.MachineCode

object PCRelHiRelocator32 : InstructionRelocator32 {
    override operator fun invoke(mcode: MachineCode, pc: Int, target: Int) {
        mcode[InstructionField.IMM_31_12] = (target - pc + 0x800) shr 12
    }
}
