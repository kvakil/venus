package venus.riscv.insts

import venus.riscv.insts.dsl.ShiftImmediateInstruction

val srli = ShiftImmediateInstruction(
        name = "srli",
        funct3 = 0b101,
        funct7 = 0b0000000,
        eval32 = { a, b -> if (b == 0) a else (a ushr b) }
)
