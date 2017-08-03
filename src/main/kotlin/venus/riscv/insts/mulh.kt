package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction

val mulh = RTypeInstruction(
        name = "mulh",
        opcode = 0b0110011,
        funct3 = 0b001,
        funct7 = 0b0000001,
        eval32 = { a, b ->
            val x = a.toLong()
            val y = b.toLong()
            ((x * y) ushr 32).toInt()
        }
)
