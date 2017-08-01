package venus.riscv.insts

import venus.riscv.insts.dsl.LoadInstruction

val lw = LoadInstruction(
        name = "lw",
        length = 4,
        opcode = 0b0000011,
        funct3 = 0b010,
        eval32 = { sim, vrs1, imm -> sim.loadWord(vrs1 + imm) }
)
