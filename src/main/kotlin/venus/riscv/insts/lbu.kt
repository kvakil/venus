package venus.riscv.insts

import venus.riscv.insts.dsl.LoadInstruction

val lbu = LoadInstruction(
        name = "lbu",
        length = 4,
        opcode = 0b0000011,
        funct3 = 0b100,
        eval32 = { sim, vrs1, imm -> sim.loadByte(vrs1 + imm) }
)
