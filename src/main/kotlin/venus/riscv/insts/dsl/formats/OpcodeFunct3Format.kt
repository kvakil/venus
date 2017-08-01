package venus.riscv.insts.dsl.formats

import venus.riscv.InstructionField

open class OpcodeFunct3Format(opcode: Int, funct3: Int) : InstructionFormat by FieldEqualFormat(listOf(
        FieldEqual(InstructionField.OPCODE, opcode),
        FieldEqual(InstructionField.FUNCT3, funct3)
))
