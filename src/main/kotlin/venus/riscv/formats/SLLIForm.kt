package venus.riscv.formats

import venus.riscv.InstructionFormat
import venus.riscv.FieldEqual
import venus.riscv.InstructionField

val SLLIForm = InstructionFormat(listOf(
    FieldEqual(InstructionField.OPCODE, 0b0110011),
    FieldEqual(InstructionField.FUNCT3, 0b001),
    FieldEqual(InstructionField.FUNCT7, 0b0000000)
))
