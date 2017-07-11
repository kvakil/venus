package venus.simulator.impls

import venus.riscv.Instruction
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation

object ECALLImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        println("ecall me!")
    }
}
