package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction

val blt = BTypeInstruction(
        name = "blt",
        length = 4,
        opcode = 0b1100011,
        funct3 = 0b100,
        cond32 = { a, b -> a < b }
)
