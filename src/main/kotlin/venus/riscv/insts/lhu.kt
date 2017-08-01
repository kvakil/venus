package venus.riscv.insts

import venus.riscv.insts.dsl.LoadInstruction

val lhu = LoadInstruction(
        name = "lhu",
        length = 4,
        opcode = 0b0000011,
        funct3 = 0b101,
        eval32 = { sim, vrs1, imm -> sim.loadHalfWord(vrs1 + imm) }
)
