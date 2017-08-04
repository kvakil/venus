package venus.riscv.insts.dsl.relocators

import venus.riscv.InstructionField
import venus.riscv.MachineCode

private object PCRelHiRelocator32 : Relocator32 {
    override operator fun invoke(mcode: MachineCode, pc: Int, target: Int) {
        mcode[InstructionField.IMM_31_12] = (target - pc + 0x800) shr 12
    }
}

val PCRelHiRelocator = Relocator(PCRelHiRelocator32, NoRelocator64)
