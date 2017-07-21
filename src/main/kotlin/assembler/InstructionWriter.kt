package venus.assembler

import venus.riscv.Instruction
import venus.riscv.InstructionFormat
import venus.riscv.Program

/**
 * A class which describes how to write an instruction to a program.
 *
 * By default, this sets all the required fields according to the [InstructionFormat].
 */
abstract class InstructionWriter {
    /**
     * Writes the instruction to the given program.
     *
     * This describes how to write a particular instruction (like `add`),
     * but not how to write its arguments (like `add x0 x1 x2`).
     * That is, all the work for its arguments must be done by concrete classes.
     *
     * @param prog the program to add the instruction to
     * @param iform the required format of the instruction
     * @param args the arguments for this instruction
     */
    operator fun invoke(prog: Program, iform: InstructionFormat, args: List<String>) {
        val inst = Instruction(0)
        for ((ifield, required) in iform.ifields) {
            inst.setField(ifield, required)
        }
        this(prog, inst, args)
        prog.add(inst)
    }

    /**
     * Describes how to write our instruction.
     *
     * @param prog the program to add the instruction to
     * @param inst the instruction which we are writing, which will be modified
     * @param args the arguments for this instruction
     */
    abstract operator fun invoke(prog: Program, inst: Instruction, args: List<String>)
}