package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val xor = RTypeInstruction(
        name = "xor",
        opcode = 0b0110011,
        funct3 = 0b100,
        funct7 = 0b0000000,
        eval32 = { a, b -> a xor b },
        eval64 = { a, b -> a xor b }
)
