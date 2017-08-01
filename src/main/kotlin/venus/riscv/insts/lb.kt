package venus.riscv.insts

import venus.riscv.insts.dsl.LoadInstruction
import venus.riscv.insts.dsl.signExtend

val lb = LoadInstruction(
        name = "lb",
        length = 4,
        opcode = 0b0000011,
        funct3 = 0b000,
        eval32 = { sim, vrs1, imm -> signExtend(sim.loadByte(vrs1 + imm), 8) }
)
