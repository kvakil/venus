package venus.riscv.formats

import venus.riscv.InstructionFormat
import venus.riscv.FieldEqual
import venus.riscv.InstructionField

val BNEForm = InstructionFormat(listOf(
    FieldEqual(InstructionField.OPCODE, 0b1100011),
    FieldEqual(InstructionField.FUNCT3, 0b001)
))
