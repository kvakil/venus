package venus.riscv.insts.dsl.relocators

import venus.riscv.MachineCode

object NoRelocator32 : InstructionRelocator32 {
    override operator fun invoke(mcode: MachineCode, pc: Int, target: Int)
            = throw NotImplementedError("no relocator32 for $mcode")
}
