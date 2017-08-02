package venus.riscv.insts.dsl.impls

import venus.riscv.MachineCode
import venus.simulator.Simulator

class RawImplementation(private val eval: (MachineCode, Simulator) -> Unit) : InstructionImplementation {
    override operator fun invoke(mcode: MachineCode, sim: Simulator) = eval(mcode, sim)
}
