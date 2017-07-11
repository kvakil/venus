package venus.riscv.formats

import venus.riscv.InstructionFormat
import venus.riscv.FieldEqual
import venus.riscv.InstructionField

val ECALLForm = InstructionFormat(listOf(
    FieldEqual(InstructionField.ENTIRE, 0b000000000000_00000_000_00000_1110011)
))
