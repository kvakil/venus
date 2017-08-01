package venus.riscv.insts

import venus.riscv.InstructionField
import venus.riscv.insts.dsl.UTypeInstruction
import venus.riscv.insts.dsl.impls.NoImplementation
import venus.riscv.insts.dsl.relocators.PCRelHiRelocator32

val auipc = UTypeInstruction(
        name = "auipc",
        opcode = 0b0010111,
        impl32 = { mcode, sim ->
            val offset = mcode[InstructionField.IMM_31_12] shl 12
            sim.setReg(mcode[InstructionField.RD], sim.getPC() + offset)
            sim.incrementPC(mcode.length)
        },
        impl64 = NoImplementation::invoke,
        relocator32 = PCRelHiRelocator32
)
