package venus.assembler

import venus.riscv.Instruction
import venus.riscv.InstructionFormat
import venus.riscv.Program

abstract class InstructionWriter {
    operator fun invoke(prog: Program, iform: InstructionFormat, args: List<String>) {
        val inst = Instruction(0)
        iform.ifields.forEach { (ifield, required) -> inst.setField(ifield, required) }
        this(prog, inst, args)
    }

    abstract operator fun invoke(prog: Program, inst: Instruction, args: List<String>)
}