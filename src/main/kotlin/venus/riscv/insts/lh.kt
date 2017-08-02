package venus.riscv.insts

import venus.riscv.insts.dsl.LoadTypeInstruction
import venus.riscv.insts.dsl.impls.signExtend
import venus.simulator.Simulator

val lh = LoadTypeInstruction(
        name = "lh",
        opcode = 0b0000011,
        funct3 = 0b001,
        load32 = Simulator::loadHalfWord,
        postLoad32 = { v -> signExtend(v, 16) }
)
