package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

/**
 * A singleton which can be invoked to write any load instruction.
 *
 * This differs from [ITypeWriter] in the order it takes its immediate.
 */
object LoadWriter : InstructionWriter() {
    /** Maximum value for a load immediate */
    const val MAX_I_VALUE = 2047
    /** Minimum value for a load immediate */
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
    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        checkArgsLength(args, 3)

        val rd = regNameToNumber(args[0])
        val rs1 = regNameToNumber(args[2])

        val imm = getImmediate(args[1], MIN_I_VALUE, MAX_I_VALUE)

        inst.setField(InstructionField.RD, rd)
        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.IMM_11_0, imm)
    }
}