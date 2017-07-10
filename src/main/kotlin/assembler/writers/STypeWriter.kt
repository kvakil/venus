package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object STypeWriter : InstructionWriter() {
    const val MAX_S_VALUE = 2047
    const val MIN_S_VALUE = -2048

    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        checkArgsLength(args, 3)

        val rs1 = regNameToNumber(args[1])
        val rs2 = regNameToNumber(args[2])
        val imm = getImmediate(args[0], MIN_S_VALUE, MAX_S_VALUE)

        var imm_lo = imm and 0b11111
        var imm_hi = (imm and 0b11111.inv()) shr 5

        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
        inst.setField(InstructionField.IMM_4_0, imm_lo)
        inst.setField(InstructionField.IMM_11_5, imm_hi)
        prog.add(inst)
    }
}