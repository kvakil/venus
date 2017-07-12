package venus.assembler.writers

import venus.assembler.InstructionWriter
import venus.riscv.Program
import venus.riscv.Instruction
import venus.riscv.InstructionField

object JTypeWriter : InstructionWriter() {
    const val MAX_J_VALUE = 2047
    const val MIN_J_VALUE = -2048

    override operator fun invoke(prog: Program, inst: Instruction, args: List<String>) {
        checkArgsLength(args, 2)

        val rd = regNameToNumber(args[0])

        inst.setField(InstructionField.RD, rd)
        prog.addJump(args[1])
        prog.add(inst)
    }
}