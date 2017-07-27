package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

/**
 * A singleton which can be invoked to write any S-type instruction.
 */
object STypeWriter : InstructionWriter() {
    /** Maximum immediate for an S-Type Instruction */
    const val MAX_S_VALUE = 2047
    /** Minimum immediate for an S-Type Instruction */
    const val MIN_S_VALUE = -2048

    /**
     * Sets instruction fields.
     *
     * @param prog the program to add the instruction to
     * @param inst the instruction to fill in and add
     * @param args the arguments given in the code
     *
     * @throws venus.assembler.AssemblerError if an invalid register is given
     * @throws venus.assembler.AssemblerError if the immediate is out of range
     * @throws venus.assembler.AssemblerError if the wrong number of arguments is given
     */
    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        checkArgsLength(args, 3)

        val rs1 = regNameToNumber(args[2])
        val rs2 = regNameToNumber(args[0])
        val imm = getImmediate(args[1], MIN_S_VALUE, MAX_S_VALUE)

        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
        inst.setField(InstructionField.IMM_4_0, imm)
        inst.setField(InstructionField.IMM_11_5, imm shr 5)
    }
}