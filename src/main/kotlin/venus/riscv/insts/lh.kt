package venus.riscv.insts

import venus.riscv.insts.dsl.LoadInstruction
import venus.riscv.insts.dsl.signExtend

val lh = LoadInstruction(
        name = "lh",
        length = 4,
        opcode = 0b0000011,
        funct3 = 0b001,
        eval32 = { sim, vrs1, imm -> signExtend(sim.loadHalfWord(vrs1 + imm), 16) }
)
