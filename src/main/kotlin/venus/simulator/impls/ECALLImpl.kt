package venus.simulator.impls

import venus.riscv.Instruction
import venus.simulator.Simulator
import venus.simulator.InstructionImplementation
import venus.glue.Renderer

object ECALLImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, sim: Simulator) {
        val which = sim.getReg(10)
        when (which) {
            1 -> { // print integer
                val arg = sim.getReg(11)
                Renderer.printConsole(arg)
            }
            4 -> { // print string
                var arg = sim.getReg(11)
                var c = sim.loadByte(arg)
                arg++
                while (c != 0) {
                    Renderer.printConsole(c.toChar())
                    c = sim.loadByte(arg)
                    arg++
                }
            }
            9 -> { // malloc
                var bytes = sim.getReg(11)
                if (bytes < 0) return
                sim.setReg(10, sim.getHeapEnd())
                sim.addHeapSpace(bytes)
            }
            10 -> { // exit
                sim.setPC(Int.MAX_VALUE)
                return
            }
            11 -> { // print char
                val arg = sim.getReg(11)
                Renderer.printConsole(arg.toChar())
            }
            17 -> { // terminate with error code
                sim.setPC(Int.MAX_VALUE)
                val retVal = sim.getReg(11)
                Renderer.printConsole("Exited with error code $retVal\n")
            }
            else -> {
                Renderer.printConsole("Invalid ecall $which\n")
            }
        }
        sim.incrementPC(inst.length)
    }
}
