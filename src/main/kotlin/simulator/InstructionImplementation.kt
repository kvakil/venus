package venus.simulator

import venus.riscv.Instruction
import venus.simulator.SimulatorState

interface InstructionImplementation {
    operator fun invoke(inst: Instruction, state: SimulatorState);
}