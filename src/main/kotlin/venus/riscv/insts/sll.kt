package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val sll = RTypeInstruction(
        name = "sll",
        opcode = 0b0110011,
        funct3 = 0b001,
        funct7 = 0b0000000,
        eval32 = { a, b -> a shl (b and 0b11111) },
        eval64 = { a, b -> a shl (b.toInt() and 0b111111) }
)
