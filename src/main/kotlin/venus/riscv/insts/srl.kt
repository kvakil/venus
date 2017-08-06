package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val srl = RTypeInstruction(
        name = "srl",
        opcode = 0b0110011,
        funct3 = 0b101,
        funct7 = 0b0000000,
        eval32 = { a, b -> a ushr (b and 0b11111) },
        eval64 = { a, b -> a ushr (b.toInt() and 0b111111) }
)
