package venus.riscv.formats

import venus.riscv.InstructionFormat
import venus.riscv.FieldEqual
import venus.riscv.InstructionField

val SWForm = InstructionFormat(listOf(
    FieldEqual(InstructionField.OPCODE, 0b0100011),
    FieldEqual(InstructionField.FUNCT3, 0b010)
))
