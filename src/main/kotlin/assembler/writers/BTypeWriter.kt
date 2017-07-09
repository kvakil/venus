package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object BTypeWriter : InstructionWriter() {
    const val MAX_B_VALUE = 2047
    const val MIN_B_VALUE = -2048

    override operator fun invoke(prog: Program, inst: Instruction, args: Array<String>): String {
        if (args.size < 3)
            return "too few arguments, got ${args.size}, need 3"
        else if (args.size > 3)
            return "too many arguments, got ${args.size}, need 3"

        val rs1 = regNameToNumber(args[0])
        val rs2 = regNameToNumber(args[1])
        if (rs1 == -1) return "invalid register rs1, got ${args[0]}"
        if (rs2 == -1) return "invalid register rs2, got ${args[1]}"

        val imm = prog.getLabelOffset(args[2])
        if (imm == null) return "couldn't find label ${args[2]}"

        if (imm < MIN_B_VALUE)
            return "branch too far back"
        if (imm > MAX_B_VALUE)
            return "branch too far forward"

        var imm_12 = imm and 0b1000000000000
        var imm_11 = imm and 0b0100000000000
        var imm_10_5 = imm and 0b0011111000000
        var imm_4_1 = imm and 0b0000000011110

        inst.setField(InstructionField.IMM_12, imm_12)
        inst.setField(InstructionField.IMM_11_B, imm_11)
        inst.setField(InstructionField.IMM_10_5, imm_10_5)
        inst.setField(InstructionField.IMM_4_1, imm_4_1)
        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
        prog.add(inst)
        return ""
    }
}