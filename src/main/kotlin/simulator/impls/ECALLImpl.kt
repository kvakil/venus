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
                print(arg)
            }
            4 -> { // print string
                var arg = state.getReg(11)
                var c = state.mem.loadByte(arg)
                arg++
                while (c != 0) {
                    print(c.toChar())
                    c = state.mem.loadByte(arg)
                    arg++
                }
            }
            9 -> { // malloc
                val bytes = state.getReg(11)
                if (bytes < 0) return
                state.setReg(10, state.heapEnd)
                state.heapEnd += bytes
            }
            10 -> { // exit
                state.pc = Int.MAX_VALUE
                return
            }
            11 -> { // print char
                var arg = state.getReg(11)
                print(arg.toChar())
            }
            else -> {
                println("Invalid ecall ${which}")
            }
        }
        state.pc += 4
    }
}
