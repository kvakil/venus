package venus.riscv

import venus.riscv.InstructionFormat

class ITypeInstruction(encoding: Int) : Instruction(encoding) {
    val rd: Int get() = encoding and InstructionFormat.RD.mask
    val funct3: Int get() = encoding and InstructionFormat.FUNCT3.mask
    val rs1: Int get() = encoding and InstructionFormat.RS1.mask
    val imm_11_0: Int get() = encoding and InstructionFormat.IMM_11_0.mask
}