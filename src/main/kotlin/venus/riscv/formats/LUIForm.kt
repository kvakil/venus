package venus.riscv.formats

import venus.riscv.InstructionFormat
import venus.riscv.FieldEqual
import venus.riscv.InstructionField

val LUIForm = InstructionFormat(listOf(
    FieldEqual(InstructionField.OPCODE, 0b0110111)
))
