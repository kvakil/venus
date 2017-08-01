package venus.riscv.insts.dsl.impls

import venus.riscv.MachineCode
import venus.simulator.Simulator

object NoImplementation : InstructionImplementation {
    override operator fun invoke(mcode: MachineCode, sim: Simulator) =
            throw NotImplementedError("no implementation available")
}
