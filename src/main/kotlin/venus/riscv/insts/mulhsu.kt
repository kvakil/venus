package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val mulhsu = RTypeInstruction(
        name = "mulhsu",
        opcode = 0b0110011,
        funct3 = 0b010,
        funct7 = 0b0000001,
        eval32 = { a, b ->
            val x = a.toLong()
            val y = (b.toLong() shl 32) ushr 32
            ((x * y) ushr 32).toInt()
        }
)
