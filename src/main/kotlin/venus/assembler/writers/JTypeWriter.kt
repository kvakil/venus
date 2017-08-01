package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program

/**
 * A singleton which can be invoked to write any J-type instruction.
 */
object JTypeWriter : InstructionWriter() {
    /** Maximum value for a J-type immediate */
    const val MAX_J_VALUE = 2047
    /** Minimum value for a J-type immediate */
    const val MIN_J_VALUE = -2048

    /**
     * Sets instruction fields, adds the instruction to the relocation table.
     *
     * @param prog the program to add the instruction to
     * @param inst the instruction to fill in and add
     * @param args the arguments given in the code
     *
     * @throws venus.assembler.AssemblerError if an invalid register is given
     * @throws venus.assembler.AssemblerError if the wrong number of arguments is given
     */
    override operator fun invoke(prog: Program, inst: MachineCode, args: List<String>) {
        checkArgsLength(args, 2)

        val rd = regNameToNumber(args[0])

        inst.setField(InstructionField.RD, rd)
        prog.addRelocation(args[1])
    }
}