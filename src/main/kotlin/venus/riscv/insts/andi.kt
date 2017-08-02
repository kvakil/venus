package venus.riscv.insts

import venus.riscv.insts.dsl.ITypeInstruction

val andi = ITypeInstruction(
        name = "andi",
        opcode = 0b0010011,
        funct3 = 0b111,
        eval32 = { a, b -> a and b },
        eval64 = { a, b -> a and b }
)
