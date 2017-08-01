package venus.riscv.insts

import venus.riscv.insts.dsl.STypeInstruction
import venus.simulator.Simulator

val sb = STypeInstruction(
        name = "sb",
        length = 4,
        opcode = 0b0100011,
        funct3 = 0b000,
        store32 = Simulator::storeByte
)
