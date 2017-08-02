package venus.riscv.insts

import venus.riscv.insts.dsl.LoadTypeInstruction
import venus.simulator.Simulator

val lbu = LoadTypeInstruction(
        name = "lbu",
        opcode = 0b0000011,
        funct3 = 0b100,
        load32 = Simulator::loadByte
)
