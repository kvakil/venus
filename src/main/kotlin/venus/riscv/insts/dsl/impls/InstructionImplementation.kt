package venus.riscv.insts.dsl.impls

import venus.riscv.MachineCode
import venus.simulator.Simulator

interface InstructionImplementation {
    operator fun invoke(mcode: MachineCode, sim: Simulator)
}
