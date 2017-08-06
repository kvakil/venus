package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val sll = RTypeInstruction(
        name = "sll",
        opcode = 0b0110011,
        funct3 = 0b001,
        funct7 = 0b0000000,
        eval32 = { a, b -> a shl b },
        eval64 = { a, b -> a shl b.toInt() }
)
