package venus.riscv.insts.dsl.parsers

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.riscv.insts.dsl.getImmediate
import venus.riscv.insts.dsl.regNameToNumber

object ITypeParser : InstructionParser {
    override operator fun invoke(prog: Program, mcode: MachineCode, args: List<String>) {
        mcode[InstructionField.RD] = regNameToNumber(args[0])
        mcode[InstructionField.RS1] = regNameToNumber(args[1])
        mcode[InstructionField.IMM_11_0] = getImmediate(args[2], -2048, 2047)
    }
}
