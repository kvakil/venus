package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.assembler.Program
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
        prog.add(inst)
        prog.addBranch(args[2])
    }
}