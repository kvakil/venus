package venus.simulator.impls

import venus.riscv.Instruction
import venus.simulator.Simulator
import venus.simulator.InstructionImplementation

object ECALLImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, sim: Simulator) {
        val which = sim.state.getReg(10)
        when (which) {
            1 -> { // print integer
                val arg = sim.state.getReg(11)
                print(arg)
            }
            4 -> { // print string
                var arg = sim.state.getReg(11)
                var c = sim.state.mem.loadByte(arg)
                arg++
                while (c != 0) {
                    print(c.toChar())
                    c = sim.state.mem.loadByte(arg)
                    arg++
                }
            }
            9 -> { // malloc
                val bytes = sim.state.getReg(11)
                if (bytes < 0) return
                sim.state.setReg(10, sim.state.heapEnd)
                sim.state.heapEnd += bytes
            }
            10 -> { // exit
                sim.state.pc = Int.MAX_VALUE
                return
            }
            11 -> { // print char
                var arg = sim.state.getReg(11)
                print(arg.toChar())
            }
            else -> {
                println("Invalid ecall ${which}")
            }
        }
        sim.incrementPC(inst.length)
    }
}
