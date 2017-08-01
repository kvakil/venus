package venus.riscv.insts

import venus.riscv.insts.dsl.ITypeInstruction

val ori = ITypeInstruction(
        name = "ori",
        length = 4,
        opcode = 0b0010011,
        funct3 = 0b110,
        eval32 = { a, b -> a or b }
)
