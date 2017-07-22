package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.Program
import venus.riscv.Instruction

/**
 * A singleton which can be invoked to do nothing (aside from what [InstructionWriter] does).
 *
 * Useful for instructions where all fields are already specified (e.g., ecall)
 */
object DoNothingWriter : InstructionWriter() {
    /**
     * Does nothing (aside from what [InstructionWriter] does).
     *
     * @param prog the program to add the instruction to
     * @param inst the instruction to fill in and add
     * @param args the arguments given in the code
     *
     * @throws venus.assembler.AssemblerError if any arguments are given
     */
    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        checkArgsLength(args, 0)
    }
}