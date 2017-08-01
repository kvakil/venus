package venus.riscv.insts

import venus.riscv.insts.dsl.STypeInstruction
import venus.simulator.Simulator

val sh = STypeInstruction(
        name = "sh",
        length = 4,
        opcode = 0b0100011,
        funct3 = 0b001,
        store32 = Simulator::storeHalfWord
)
