package venus.riscv.insts

import venus.riscv.insts.dsl.ITypeInstruction
import venus.riscv.insts.dsl.relocators.PCRelLoRelocator32

val addi = ITypeInstruction(
        name = "addi",
        opcode = 0b0010011,
        funct3 = 0b000,
        eval32 = { a, b -> a + b },
        eval64 = { a, b -> a + b },
        relocator32 = PCRelLoRelocator32
)
