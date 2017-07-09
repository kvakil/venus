package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object ITypeWriter : InstructionWriter() {
    const val MAX_I_VALUE = 2047
    const val MIN_I_VALUE = -2048

    override operator fun invoke(prog: Program, inst: Instruction, args: Array<String>): String {
        if (args.size < 3)
            return "too few arguments, got ${args.size}, need 3"
        else if (args.size > 3)
            return "too many arguments, got ${args.size}, need 3"

        val rd = regNameToNumber(args[0])
        val rs1 = regNameToNumber(args[1])
        if (rd == -1) return "invalid register rd, got ${args[0]}"
        if (rs1 == -1) return "invalid register rs1, got ${args[1]}"

        val imm: Int
        try {
            imm = args[2].toInt()
        } catch (e: NumberFormatException) {
            return "invalid number, got ${args[2]} (might be too large?)"
        }

        if (imm < MIN_I_VALUE)
            return "immediate too negative, got ${args[2]}, need >= ${MIN_I_VALUE}"
        if (imm > MAX_I_VALUE)
            return "immediate too positive, got ${args[2]}, need <= ${MAX_I_VALUE}"

        inst.setField(InstructionField.RD, rd)
        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.IMM_11_0, imm)
        prog.add(inst)
        return ""
    }
}