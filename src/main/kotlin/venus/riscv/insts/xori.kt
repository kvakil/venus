package venus.riscv.insts

import venus.riscv.insts.dsl.ITypeInstruction

val xori = ITypeInstruction(
        name = "xori",
        opcode = 0b0010011,
        funct3 = 0b100,
        eval32 = { a, b -> a xor b },
        eval64 = { a, b -> a xor b }
)
