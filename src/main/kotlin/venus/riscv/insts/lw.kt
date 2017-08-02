package venus.riscv.insts

import venus.riscv.insts.dsl.LoadTypeInstruction
import venus.simulator.Simulator

val lw = LoadTypeInstruction(
        name = "lw",
        opcode = 0b0000011,
        funct3 = 0b010,
        load32 = Simulator::loadWord
)
