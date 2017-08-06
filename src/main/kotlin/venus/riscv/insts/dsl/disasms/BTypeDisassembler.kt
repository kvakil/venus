package venus.riscv.insts.dsl.disasms

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.insts.dsl.Instruction
import venus.riscv.insts.dsl.impls.constructBranchImmediate

object BTypeDisassembler : InstructionDisassembler {
    override fun invoke(mcode: MachineCode): String {
        val name = Instruction[mcode].name
        val rs1 = mcode[InstructionField.RS1]
        val rs2 = mcode[InstructionField.RS2]
        val imm = constructBranchImmediate(mcode)
        return "$name x$rs1 x$rs2 $imm"
    }
}
