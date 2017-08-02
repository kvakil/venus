package venus.riscv.insts.dsl.relocators

import venus.riscv.MachineCode

interface InstructionRelocator32 {
    operator fun invoke(mcode: MachineCode, pc: Int, target: Int)
}
