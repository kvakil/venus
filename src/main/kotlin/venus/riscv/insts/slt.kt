package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val slt = RTypeInstruction(
        name = "slt",
        length = 4,
        opcode = 0b0110011,
        funct3 = 0b010,
        funct7 = 0b0000000,
        eval32 = { a, b -> if (a < b) 1 else 0 }
)
