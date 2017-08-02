package venus.riscv.insts

import venus.riscv.insts.dsl.STypeInstruction
import venus.simulator.Simulator

val sh = STypeInstruction(
        name = "sh",
        opcode = 0b0100011,
        funct3 = 0b000,
        store32 = Simulator::storeHalfWord
)
