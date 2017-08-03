package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val remu = RTypeInstruction(
        name = "remu",
        opcode = 0b0110011,
        funct3 = 0b111,
        funct7 = 0b0000001,
        eval32 = { a, b ->
            val x = a.toLong() shl 32 ushr 32
            val y = b.toLong() shl 32 ushr 32
            if (b == 0) a
            else (x % y).toInt()
        }
)
