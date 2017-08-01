package venus.riscv.insts

import venus.riscv.insts.dsl.BTypeInstruction
import venus.riscv.insts.dsl.compareUnsigned

val bltu = BTypeInstruction(
        name = "bltu",
        length = 4,
        opcode = 0b1100011,
        funct3 = 0b110,
        cond32 = { a, b -> compareUnsigned(a, b) < 0 }
)
