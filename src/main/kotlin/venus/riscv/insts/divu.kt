package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val divu = RTypeInstruction(
        name = "divu",
        opcode = 0b0110011,
        funct3 = 0b101,
        funct7 = 0b0000001,
        eval32 = { a, b ->
            val x = a.toLong() shl 32 ushr 32
            val y = b.toLong() shl 32 ushr 32
            if (y == 0L) a
            else (x / y).toInt()
        }
)
