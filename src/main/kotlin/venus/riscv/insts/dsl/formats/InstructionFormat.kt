package venus.riscv.insts.dsl.formats

import venus.riscv.MachineCode

interface InstructionFormat {
    fun matches(mcode: MachineCode): Boolean
    fun fill(): MachineCode
}
