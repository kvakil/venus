package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.assembler.AssemblerError
import venus.riscv.Instruction
import venus.riscv.InstructionField

object BTypeWriter : InstructionWriter() {
    const val MAX_B_VALUE = 2047
    const val MIN_B_VALUE = -2048

    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        checkArgsLength(args, 3)

        val rs1 = regNameToNumber(args[0])
        val rs2 = regNameToNumber(args[1])

        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
        val imm = prog.getLabelOffset(args[2])
        if (imm == null) throw AssemblerError("could not find label ${args[2]}")
        /* TODO: add branch bounds checking */
        val imm_11 = imm shr 11
        val imm_4_1 = imm shr 1
        val imm_12 = imm shr 12
        val imm_10_5 = imm shr 5

        inst.setField(InstructionField.IMM_11_B, imm_11)
        inst.setField(InstructionField.IMM_4_1, imm_4_1)
        inst.setField(InstructionField.IMM_12, imm_12)
        inst.setField(InstructionField.IMM_10_5, imm_10_5)

        prog.add(inst)
    }
}