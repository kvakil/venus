package venus.riscv.insts.dsl.relocators

import venus.riscv.MachineCode

interface InstructionRelocator64 {
    operator fun invoke(mcode: MachineCode, pc: Long, target: Long)
}
