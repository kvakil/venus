package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction

val bge = BTypeInstruction(
        name = "bge",
        opcode = 0b1100011,
        funct3 = 0b101,
        cond32 = { a, b -> a >= b },
        cond64 = { a, b -> a >= b }
)
