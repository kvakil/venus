package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val sra = RTypeInstruction(
        name = "sra",
        opcode = 0b0110011,
        funct3 = 0b101,
        funct7 = 0b0100000,
        eval32 = { a, b -> a shr (b and 0b11111) },
        eval64 = { a, b -> a shr (b.toInt() and 0b111111) }
)
