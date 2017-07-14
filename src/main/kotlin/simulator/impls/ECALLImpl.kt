package venus.simulator.impls

import venus.riscv.Instruction
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation

object ECALLImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        val which = state.getReg(10)
        when (which) {
            1 -> { // print integer
                val arg = state.getReg(11)
                println(arg)
            }
            10 -> {
                state.pc = Int.MAX_VALUE
                return
            }
            else -> {
                println("Invalid ecall ${which}")
            }
        }
        state.pc += 4
    }
}
