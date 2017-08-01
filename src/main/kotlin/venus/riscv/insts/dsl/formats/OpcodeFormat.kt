package venus.riscv.insts.dsl.formats

import venus.riscv.InstructionField

open class OpcodeFormat(opcode: Int) : InstructionFormat by FieldEqualFormat(listOf(
        FieldEqual(InstructionField.OPCODE, opcode)
))
