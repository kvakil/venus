package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction

val bne = BTypeInstruction(
        name = "bne",
        opcode = 0b1100011,
        funct3 = 0b001,
        cond32 = { a, b -> a != b }
)
