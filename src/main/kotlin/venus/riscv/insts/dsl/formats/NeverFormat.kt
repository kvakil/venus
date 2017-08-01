package venus.riscv.insts.dsl.formats

import venus.riscv.MachineCode

class NeverFormat : InstructionFormat {
    override fun matches(mcode: MachineCode): Boolean = false
    override fun fill(): MachineCode = throw IllegalStateException("tried to fill a NeverFormat")
}
