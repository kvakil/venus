package venus.riscv.insts

import venus.riscv.insts.dsl.LoadTypeInstruction
import venus.riscv.insts.dsl.impls.signExtend
import venus.simulator.Simulator

val lb = LoadTypeInstruction(
        name = "lb",
        opcode = 0b0000011,
        funct3 = 0b000,
        load32 = Simulator::loadByte,
        postLoad32 = { v -> signExtend(v, 8) }
)
