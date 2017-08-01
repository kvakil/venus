package venus.riscv.insts.dsl.formats

import venus.riscv.InstructionField

class RTypeFormat(opcode: Int, funct3: Int, funct7: Int) : InstructionFormat(2, listOf(
        FieldEqual(InstructionField.OPCODE, opcode),
        FieldEqual(InstructionField.FUNCT3, funct3),
        FieldEqual(InstructionField.FUNCT7, funct7)
))
