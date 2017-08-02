package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val or = RTypeInstruction(
        name = "or",
        opcode = 0b0110011,
        funct3 = 0b110,
        funct7 = 0b0000000,
        eval32 = { a, b -> a or b },
        eval64 = { a, b -> a or b }
)
