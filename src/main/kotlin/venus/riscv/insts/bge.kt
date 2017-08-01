package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction

val bge = BTypeInstruction(
        name = "bge",
        length = 4,
        opcode = 0b1100011,
        funct3 = 0b101,
        cond32 = { a, b -> a >= b }
)
