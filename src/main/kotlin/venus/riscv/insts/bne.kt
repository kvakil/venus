package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction

val bne = BTypeInstruction(
        name = "bne",
        length = 4,
        opcode = 0b1100011,
        funct3 = 0b001,
        cond32 = { a, b -> a != b }
)
