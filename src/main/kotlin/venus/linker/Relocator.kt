package venus.linker

import venus.riscv.MachineCode

abstract class Relocator {
    abstract operator fun invoke(inst: MachineCode, pc: Int, target: Int)
}