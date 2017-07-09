package venus.riscv.formats

import venus.riscv.InstructionFormat
import venus.riscv.FieldEqual
import venus.riscv.InstructionField

val SRLIForm = InstructionFormat(listOf(
    FieldEqual(InstructionField.OPCODE, 0b0110011),
    FieldEqual(InstructionField.FUNCT3, 0b101),
    FieldEqual(InstructionField.FUNCT7, 0b0000000)
))
