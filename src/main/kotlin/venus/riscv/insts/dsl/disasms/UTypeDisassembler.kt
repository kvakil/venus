package venus.riscv.insts.dsl.disasms

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.insts.dsl.Instruction

object UTypeDisassembler : InstructionDisassembler {
    override fun invoke(mcode: MachineCode): String {
        val name = Instruction[mcode].name
        val rd = mcode[InstructionField.RD]
        val imm = mcode[InstructionField.IMM_31_12]
        return "$name x$rd $imm"
    }
}
