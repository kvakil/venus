package venus.riscv.insts

import venus.riscv.insts.dsl.ITypeInstruction

val slti = ITypeInstruction(
        name = "slti",
        length = 4,
        opcode = 0b0010011,
        funct3 = 0b010,
        eval32 = { a, b -> if (a < b) 1 else 0 }
)
