package venus.riscv.insts.dsl.parsers

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.riscv.insts.dsl.regNameToNumber

object BTypeParser : InstructionParser {
    const val B_TYPE_MIN = -2048
    const val B_TYPE_MAX = 2047
    override operator fun invoke(prog: Program, mcode: MachineCode, args: List<String>) {
        mcode[InstructionField.RS1] = regNameToNumber(args[0])
        mcode[InstructionField.RS2] = regNameToNumber(args[1])

        val label = args[2]
        val imm = prog.getLabelOffset(label) ?:
                throw IllegalStateException("could not find label $label")
        if (imm !in B_TYPE_MIN..B_TYPE_MAX)
            throw IllegalStateException("branch to $label too far")

        mcode[InstructionField.IMM_11_B] = imm shr 11
        mcode[InstructionField.IMM_4_1] = imm shr 1
        mcode[InstructionField.IMM_12] = imm shr 12
        mcode[InstructionField.IMM_10_5] = imm shr 5
    }
}
