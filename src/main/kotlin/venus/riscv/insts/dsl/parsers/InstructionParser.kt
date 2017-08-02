package venus.riscv.insts.dsl.parsers

import venus.riscv.MachineCode
import venus.riscv.Program

interface InstructionParser {
    operator fun invoke(prog: Program, mcode: MachineCode, args: List<String>)
}
