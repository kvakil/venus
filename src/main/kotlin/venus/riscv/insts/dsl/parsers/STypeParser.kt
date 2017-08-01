package venus.riscv.insts.dsl.parsers

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.riscv.insts.dsl.getImmediate
import venus.riscv.insts.dsl.regNameToNumber

object STypeParser : InstructionParser {
    const val S_TYPE_MIN = -2048
    const val S_TYPE_MAX = 2047
    override operator fun invoke(prog: Program, mcode: MachineCode, args: List<String>) {
        val imm = getImmediate(args[1], S_TYPE_MIN, S_TYPE_MAX)
        mcode[InstructionField.RS1] = regNameToNumber(args[2])
        mcode[InstructionField.RS2] = regNameToNumber(args[0])
        mcode[InstructionField.IMM_4_0] = imm
        mcode[InstructionField.IMM_11_5] = imm shr 5
    }
}
