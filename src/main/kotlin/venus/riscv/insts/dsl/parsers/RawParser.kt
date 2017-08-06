package venus.riscv.insts.dsl.parsers

import venus.riscv.MachineCode
import venus.riscv.Program

class RawParser(private val eval: (Program, MachineCode, List<String>) -> Unit) : InstructionParser {
    override fun invoke(prog: Program, mcode: MachineCode, args: List<String>) = eval(prog, mcode, args)
}
