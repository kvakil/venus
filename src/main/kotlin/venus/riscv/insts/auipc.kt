package venus.riscv.insts

import venus.riscv.InstructionField
import venus.riscv.insts.dsl.UTypeInstruction

val auipc = UTypeInstruction(
        name = "auipc",
        length = 4,
        opcode = 0b0010111,
        eval32 = { mcode, sim ->
            val rd = mcode[InstructionField.RD]
            val imm = mcode[InstructionField.IMM_31_12] shl 12
            sim.setReg(rd, sim.getPC() + imm)
            sim.incrementPC(mcode.length)
        }
)
