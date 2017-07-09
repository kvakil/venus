package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object STypeWriter : InstructionWriter() {
    const val MAX_S_VALUE = 2047
    const val MIN_S_VALUE = -2048

    override operator fun invoke(prog: Program, inst: Instruction, args: Array<String>): String {
        if (args.size < 3)
            return "too few arguments, got ${args.size}, need 3"
        else if (args.size > 3)
            return "too many arguments, got ${args.size}, need 3"

        val rs1 = regNameToNumber(args[0])
        val rs2 = regNameToNumber(args[1])
        if (rs1 == -1) return "invalid register rs1, got ${args[0]}"
        if (rs2 == -1) return "invalid register rs2, got ${args[1]}"

        val imm: Int
        try {
            imm = args[2].toInt()
        } catch (e: NumberFormatException) {
            return "invalid number, got ${args[2]} (might be too large?)"
        }

        if (imm < MIN_S_VALUE)
            return "immediate too negative, got ${args[2]}, need >= ${MIN_S_VALUE}"
        if (imm > MAX_S_VALUE)
            return "immediate too positive, got ${args[2]}, need <= ${MAX_S_VALUE}"

        var imm_lo = imm and 0b11111
        var imm_hi = imm and 0b11111.inv()

        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
        inst.setField(InstructionField.IMM_4_0, imm_lo)
        inst.setField(InstructionField.IMM_11_5, imm_hi)
        prog.add(inst)
        return ""
    }
}