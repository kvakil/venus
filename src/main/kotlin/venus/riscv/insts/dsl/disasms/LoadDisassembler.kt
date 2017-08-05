package venus.riscv.insts.dsl.disasms

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.insts.dsl.Instruction
import venus.riscv.insts.dsl.impls.signExtend

object LoadDisassembler : InstructionDisassembler {
    override fun invoke(mcode: MachineCode): String {
        val name = Instruction[mcode].name
        val rs1 = mcode[InstructionField.RS1]
        val rd = mcode[InstructionField.RD]
        val imm = signExtend(mcode[InstructionField.IMM_11_0], 12)
        return "$name $rd $imm($rs1)"
    }
}
