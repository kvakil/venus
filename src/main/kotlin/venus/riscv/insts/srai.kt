package venus.riscv.insts

import venus.riscv.insts.dsl.ShiftImmediateInstruction

val srai = ShiftImmediateInstruction(
        name = "srai",
        funct3 = 0b101,
        funct7 = 0b0100000,
        eval32 = { a, b -> if (b == 0) a else (a shr b) }
)
