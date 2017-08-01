package venus.riscv.insts

import venus.riscv.insts.dsl.ITypeInstruction

val xori = ITypeInstruction(
        name = "xori",
        length = 4,
        opcode = 0b0010011,
        funct3 = 0b100,
        eval32 = { a, b -> a xor b }
)
