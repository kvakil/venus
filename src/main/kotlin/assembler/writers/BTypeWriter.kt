package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object BTypeWriter : InstructionWriter() {
    const val MAX_B_VALUE = 2047
    const val MIN_B_VALUE = -2048

    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>): String {
        if (args.size < 3)
            return "too few arguments, got ${args.size}, need 3"
        else if (args.size > 3)
            return "too many arguments, got ${args.size}, need 3"

        val rs1 = regNameToNumber(args[0])
        val rs2 = regNameToNumber(args[1])
        if (rs1 == -1) return "invalid register rs1, got ${args[0]}"
        if (rs2 == -1) return "invalid register rs2, got ${args[1]}"

        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
        prog.add(inst)
        prog.addBranch(args[2])
        return ""
    }
}