package venus.assembler

import kotlin.test.Test
import kotlin.test.assertTrue
import venus.simulator.Simulator
import venus.linker.Linker

class AssemblerSpeedTest {
    @Test fun nopRepeat() {
        val (prog, _) = Assembler.assemble("nop\n".repeat(1000))
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertTrue(true)
    }
}
