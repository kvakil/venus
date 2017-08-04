package venus.riscv.insts.dsl.relocators

import venus.riscv.InstructionField
import venus.riscv.MachineCode

object PCRelLoRelocator32 : Relocator32 {
    override operator fun invoke(mcode: MachineCode, pc: Int, target: Int) {
        mcode[InstructionField.IMM_11_0] = target - (pc - 4)
    }
}

val PCRelLoRelocator = Relocator(PCRelLoRelocator32, NoRelocator64)
