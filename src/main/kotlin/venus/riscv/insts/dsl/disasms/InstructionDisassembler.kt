package venus.riscv.insts.dsl.disasms

import venus.riscv.MachineCode

interface InstructionDisassembler {
    operator fun invoke(mcode: MachineCode): String
}
