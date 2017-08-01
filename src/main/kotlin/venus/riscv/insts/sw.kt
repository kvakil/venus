package venus.riscv.insts

import venus.riscv.insts.dsl.STypeInstruction
import venus.simulator.Simulator

val sw = STypeInstruction(
        name = "sw",
        length = 4,
        opcode = 0b0100011,
        funct3 = 0b010,
        store32 = Simulator::storeWord
)
