package venus.riscv.insts.dsl.parsers

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program

object RTypeParser : InstructionParser {
    override operator fun invoke(prog: Program, mcode: MachineCode, args: List<String>) {
        checkArgsLength(args.size, 3)

        mcode[InstructionField.RD] = regNameToNumber(args[0])
        mcode[InstructionField.RS1] = regNameToNumber(args[1])
        mcode[InstructionField.RS2] = regNameToNumber(args[2])
    }
}
