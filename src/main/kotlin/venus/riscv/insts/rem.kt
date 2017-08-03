package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val rem = RTypeInstruction(
        name = "rem",
        opcode = 0b0110011,
        funct3 = 0b110,
        funct7 = 0b0000001,
        eval32 = { a, b ->
            if (b == 0) a
            else if (a == Int.MIN_VALUE && b == -1) 0
            else a % b
        }
)
