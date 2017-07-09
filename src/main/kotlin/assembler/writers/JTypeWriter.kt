package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object JTypeWriter : InstructionWriter() {
    const val MAX_J_VALUE = 2047
    const val MIN_J_VALUE = -2048

    override operator fun invoke(prog: Program, inst: Instruction, args: Array<String>): String {
        if (args.size < 2)
            return "too few arguments, got ${args.size}, need 2"
        else if (args.size > 2)
            return "too many arguments, got ${args.size}, need 2"

        val rd = regNameToNumber(args[0])
        if (rd == -1) return "invalid register rd, got ${args[0]}"

        inst.setField(InstructionField.RD, rd)
        prog.add(inst)
        prog.addJump(args[1])
        return ""
    }
}