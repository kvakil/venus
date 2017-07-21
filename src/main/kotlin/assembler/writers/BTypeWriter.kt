package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.Program
import venus.assembler.AssemblerError
import venus.riscv.Instruction
import venus.riscv.InstructionField

/**
 * A singleton which can be called to write any BType instruction
 */
object BTypeWriter : InstructionWriter() {
    const val MAX_B_VALUE = 2047
    const val MIN_B_VALUE = -2048

    /**
     * Sets instruction fields and adds the instruction to the given program.
     *
     * @param prog the program to add the instruction to
     * @param inst the instruction to fill in and add
     * @param args the arguments given in the code
     *
     * @throws AssemblerError if the label doesn't exist, or is too far away
     */
    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        checkArgsLength(args, 3)

        val rs1 = regNameToNumber(args[0])
        val rs2 = regNameToNumber(args[1])

        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
        val label = args[2]
        val imm = prog.getLabelOffset(label) ?:
                throw AssemblerError("could not find label $label")
        if (imm !in MIN_B_VALUE..MAX_B_VALUE)
            throw AssemblerError("branch to $label too far")

        inst.setField(InstructionField.IMM_11_B, imm shr 11)
        inst.setField(InstructionField.IMM_4_1, imm shr 1)
        inst.setField(InstructionField.IMM_12, imm shr 12)
        inst.setField(InstructionField.IMM_10_5, imm shr 5)

        prog.add(inst)
    }
}