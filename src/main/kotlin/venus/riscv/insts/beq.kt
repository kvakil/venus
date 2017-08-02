package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction

val beq = BTypeInstruction(
        name = "beq",
        opcode = 0b1100011,
        funct3 = 0b000,
        cond32 = { a, b -> a == b },
        cond64 = { a, b -> a == b }
)
