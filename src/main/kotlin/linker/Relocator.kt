package venus.linker

import venus.riscv.Instruction

abstract class Relocator {
    abstract operator fun invoke(inst: Instruction, pc: Int, target: Int)
}