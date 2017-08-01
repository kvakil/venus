package venus.riscv.insts

import venus.riscv.InstructionField
import venus.riscv.insts.dsl.*

private const val MIN_I_VALUE = -2048
private const val MAX_I_VALUE = 2047

val jalr = ManualInstruction(
        name = "jalr",
        length = 4,
        fieldPredicates = listOf<FieldEqual>(
                FieldEqual(InstructionField.OPCODE, 0b1100111),
                FieldEqual(InstructionField.FUNCT3, 0b000)
        ),
        eval32 = { mcode, sim ->
            val rs1 = mcode[InstructionField.RS1]
            val imm = signExtend(mcode[InstructionField.IMM_11_0], 12)
            val rd = mcode[InstructionField.RD]
            sim.setReg(rd, sim.getPC() + mcode.length)
            sim.setPC(((sim.getReg(rs1) + imm) shr 1) shl 1)
        },
        filler = { _, mcode, args ->
            mcode[InstructionField.RD] = regNameToNumber(args[0])
            mcode[InstructionField.RS1] = regNameToNumber(args[1])
            mcode[InstructionField.IMM_11_0] = getImmediate(args[2], MIN_I_VALUE, MAX_I_VALUE)
        }
)
