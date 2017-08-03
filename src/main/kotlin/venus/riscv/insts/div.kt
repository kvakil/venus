package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val div = RTypeInstruction(
        name = "div",
        opcode = 0b0110011,
        funct3 = 0b100,
        funct7 = 0b0000001,
        eval32 = { a, b ->
            if (b == 0) -1
            else if (a == Int.MIN_VALUE && b == -1) a
            else a / b
        }
)
