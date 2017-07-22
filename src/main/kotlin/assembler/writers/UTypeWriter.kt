package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

/**
 * A singleton which can be invoked to write any U-type instruction.
 */
object UTypeWriter : InstructionWriter() {
    /** Maximum immediate for a U-Type Instruction */
    const val MAX_U_VALUE = 1048575
    /** Minimum immediate for a U-Type Instruction */
    const val MIN_U_VALUE = 0
    
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
        checkArgsLength(args, 2)

        val rd = regNameToNumber(args[0])

        val imm = getImmediate(args[1], MIN_U_VALUE, MAX_U_VALUE)

        inst.setField(InstructionField.IMM_31_12, imm)
        inst.setField(InstructionField.RD, rd)
    }
}