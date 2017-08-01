package venus.riscv.insts

import venus.riscv.insts.dsl.RTypeInstruction
import venus.riscv.insts.dsl.compareUnsigned

val sltu = RTypeInstruction(
        name = "sltu",
        length = 4,
        opcode = 0b0110011,
        funct3 = 0b011,
        funct7 = 0b0000000,
        eval32 = { a, b -> if (compareUnsigned(a, b) < 0) 1 else 0 }
)
