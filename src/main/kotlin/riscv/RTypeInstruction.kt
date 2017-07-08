package venus.riscv

import venus.riscv.InstructionFormat

class RTypeInstruction(encoding: Int) : Instruction(encoding) {
    val rd: Int get() = encoding and InstructionFormat.RD.mask
    val funct3: Int get() = encoding and InstructionFormat.FUNCT3.mask
    val rs1: Int get() = encoding and InstructionFormat.RS1.mask
    val rs2: Int get() = encoding and InstructionFormat.RS2.mask
    val funct7: Int get() = encoding and InstructionFormat.FUNCT7.mask
}