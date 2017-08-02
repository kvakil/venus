package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction

val blt = BTypeInstruction(
        name = "blt",
        opcode = 0b1100011,
        funct3 = 0b100,
        cond32 = { a, b -> a < b },
        cond64 = { a, b -> a < b }
)
