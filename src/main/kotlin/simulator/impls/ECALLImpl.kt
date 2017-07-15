package venus.simulator.impls

import venus.riscv.Instruction
import venus.simulator.Simulator
import venus.simulator.InstructionImplementation

object ECALLImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, sim: Simulator) {
        val which = sim.getReg(10)
        when (which) {
            1 -> { // print integer
                val arg = sim.getReg(11)
                print(arg)
            }
            4 -> { // print string
                var arg = sim.getReg(11)
                var c = sim.loadByte(arg)
                arg++
                while (c != 0) {
                    print(c.toChar())
                    c = sim.loadByte(arg)
                    arg++
                }
            }
            9 -> { // malloc
                TODO("implement malloc")
            }
            10 -> { // exit
                sim.setPC(Int.MAX_VALUE)
                return
            }
            11 -> { // print char
                var arg = sim.getReg(11)
                print(arg.toChar())
            }
            else -> {
                println("Invalid ecall ${which}")
            }
        }
        sim.incrementPC(inst.length)
    }
}
