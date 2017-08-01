package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction
import venus.riscv.insts.dsl.compareUnsigned

val bgeu = BTypeInstruction(
        name = "bgeu",
        length = 4,
        opcode = 0b1100011,
        funct3 = 0b111,
        cond32 = { a, b -> compareUnsigned(a, b) >= 0 }
)
