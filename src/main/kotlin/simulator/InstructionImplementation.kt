package venus.simulator

import venus.riscv.Instruction
import venus.simulator.SimulatorState

interface InstructionImplementation {
    fun exec(inst: Instruction, state: SimulatorState);
}