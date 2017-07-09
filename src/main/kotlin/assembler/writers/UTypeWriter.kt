package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object UTypeWriter : InstructionWriter() {
    const val MAX_U_VALUE = 524287
    const val MIN_U_VALUE = -524288

    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>): String {
        if (args.size < 2)
            return "too few arguments, got ${args.size}, need 2"
        else if (args.size > 2)
            return "too many arguments, got ${args.size}, need 2"

        val rd = regNameToNumber(args[0])
        if (rd == -1) return "invalid register rd, got ${args[0]}"

        val imm: Int
        try {
            imm = args[1].toInt()
        } catch (e: NumberFormatException) {
            return "invalid number, got ${args[1]} (might be too large?)"
        }

        if (imm < MIN_U_VALUE)
            return "immediate too negative, got ${args[1]}, need >= ${MIN_U_VALUE}"
        if (imm > MAX_U_VALUE)
            return "immediate too positive, got ${args[1]}, need <= ${MAX_U_VALUE}"

        inst.setField(InstructionField.IMM_31_12, imm)
        inst.setField(InstructionField.RD, rd)
        prog.add(inst)
        return ""
    }
}