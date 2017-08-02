package venus.riscv.insts

import venus.riscv.insts.dsl.ITypeInstruction
import venus.riscv.insts.dsl.compareUnsigned
import venus.riscv.insts.dsl.compareUnsignedLong

val sltiu = ITypeInstruction(
        name = "sltiu",
        opcode = 0b0010011,
        funct3 = 0b011,
        eval32 = { a, b -> if (compareUnsigned(a, b) < 0) 1 else 0 },
        eval64 = { a, b -> if (compareUnsignedLong(a, b) < 0) 1 else 0 }
)
