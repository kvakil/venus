package venus.riscv.insts.dsl.relocators

import venus.riscv.MachineCode

object NoRelocator : InstructionRelocator {
    override operator fun invoke(mcode: MachineCode, pc: Int, target: Int)
            = throw NotImplementedError("no relocator for $mcode")
}
