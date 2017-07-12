package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object RTypeWriter : InstructionWriter() {
    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        checkArgsLength(args, 3)

        val rd = regNameToNumber(args[0])
        val rs1 = regNameToNumber(args[1])
        val rs2 = regNameToNumber(args[2])

        inst.setField(InstructionField.RD, rd)
        inst.setField(InstructionField.RS1, rs1)
        inst.setField(InstructionField.RS2, rs2)
        prog.add(inst)
    }
}