package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction

val beq = BTypeInstruction(
        name = "beq",
        length = 4,
        opcode = 0b1100011,
        funct3 = 0b000,
        cond32 = { a, b -> a == b }
)
