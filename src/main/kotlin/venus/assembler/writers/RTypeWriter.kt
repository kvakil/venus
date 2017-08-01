package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program

/**
 * A singleton which can be invoked to write any R-type instruction.
 */
object RTypeWriter : InstructionWriter() {
    /**
     * Sets instruction fields.
     *
     * @param prog the program to add the instruction to
     * @param inst the instruction to fill in and add
     * @param args the arguments given in the code
     *
     * @throws venus.assembler.AssemblerError if an invalid register is given
     * @throws venus.assembler.AssemblerError if the wrong number of arguments is given
     */
    override operator fun invoke(prog: Program, inst: MachineCode, args: List<String>) {
        checkArgsLength(args, 3)

        val rd = regNameToNumber(args[0])
        val rs1 = regNameToNumber(args[1])
        val rs2 = regNameToNumber(args[2])

        inst.setField(InstructionField.RD, rd)
        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
    }
}