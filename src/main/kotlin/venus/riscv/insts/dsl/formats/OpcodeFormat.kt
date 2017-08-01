package venus.riscv.insts.dsl.formats

import venus.riscv.InstructionField

open class OpcodeFormat(opcode: Int) : InstructionFormat(2, listOf(
        FieldEqual(InstructionField.OPCODE, opcode)
))
