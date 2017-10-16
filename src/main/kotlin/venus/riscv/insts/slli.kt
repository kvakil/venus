package venus.riscv.insts

import venus.riscv.insts.dsl.ShiftImmediateInstruction

val slli = ShiftImmediateInstruction(
        name = "slli",
        funct3 = 0b001,
        funct7 = 0b0000000,
        eval32 = { a, b -> if (b == 0) a else (a shl b) }
)
