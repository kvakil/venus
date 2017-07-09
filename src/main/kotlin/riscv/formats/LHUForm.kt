package venus.riscv.formats

import venus.riscv.InstructionFormat
import venus.riscv.FieldEqual
import venus.riscv.InstructionField

val LHUForm = InstructionFormat(listOf(
    FieldEqual(InstructionField.OPCODE, 0b0000011),
    FieldEqual(InstructionField.FUNCT3, 0b101)
))
