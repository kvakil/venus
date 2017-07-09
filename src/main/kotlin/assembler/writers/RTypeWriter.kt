package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object RTypeWriter : InstructionWriter() {
    override operator fun invoke(prog: Program, inst: Instruction, args: Array<String>): String {
        if (args.size < 3)
            return "too few arguments, got ${args.size}, need 3"
        else if (args.size > 3)
            return "too many arguments, got ${args.size}, need 3"

        val rd = regNameToNumber(args[0])
        val rs1 = regNameToNumber(args[1])
        val rs2 = regNameToNumber(args[2])
        if (rd == -1) return "invalid register rd, got ${args[0]}"
        if (rs1 == -1) return "invalid register rs1, got ${args[1]}"
        if (rs2 == -1) return "invalid register rs2, got ${args[2]}"

        inst.setField(InstructionField.RD, rd)
        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
        prog.add(inst)
        return ""
    }
}