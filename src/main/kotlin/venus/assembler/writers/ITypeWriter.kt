package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program

/**
 * A singleton which can be invoked to write any I-type instruction.
 */
object ITypeWriter : InstructionWriter() {
    /** Maximum value for an I-type immediate */
    const val MAX_I_VALUE = 2047
    /** Minimum value for an I-type immediate */
    const val MIN_I_VALUE = -2048

    /**
     * Sets instruction fields.
     *
     * @param prog the program to add the instruction to
     * @param inst the instruction to fill in and add
     * @param args the arguments given in the code
     *
     * @throws venus.assembler.AssemblerError if an invalid register is given
     * @throws venus.assembler.AssemblerError if the wrong number of arguments is given
     * @throws venus.assembler.AssemblerError if the immediate is out of range
     */
    override operator fun invoke(prog: Program, inst: MachineCode, args: List<String>) {
        checkArgsLength(args, 3)

        val rd = regNameToNumber(args[0])
        val rs1 = regNameToNumber(args[1])

        val imm = getImmediate(args[2], MIN_I_VALUE, MAX_I_VALUE)

        inst.setField(InstructionField.RD, rd)
        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.IMM_11_0, imm)
    }
}