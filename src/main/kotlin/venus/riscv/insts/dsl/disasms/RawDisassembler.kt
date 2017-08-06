package venus.riscv.insts.dsl.disasms

import venus.riscv.MachineCode

class RawDisassembler(private val disasm: (MachineCode) -> String) : InstructionDisassembler {
    override fun invoke(mcode: MachineCode): String = disasm(mcode)
}
