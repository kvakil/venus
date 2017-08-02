package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val and = RTypeInstruction(
        name = "and",
        opcode = 0b0110011,
        funct3 = 0b111,
        funct7 = 0b0000000,
        eval32 = { a, b -> a and b },
        eval64 = { a, b -> a and b }
)
