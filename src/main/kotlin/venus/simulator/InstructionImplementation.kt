package venus.simulator

import venus.riscv.MachineCode

interface InstructionImplementation {
    operator fun invoke(inst: MachineCode, sim: Simulator)
}