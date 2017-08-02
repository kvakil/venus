package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val add = RTypeInstruction(
        name = "add",
        opcode = 0b0110011,
        funct3 = 0b000,
        funct7 = 0b0000000,
        eval32 = { a, b -> a + b },
        eval64 = { a, b -> a + b }
)
