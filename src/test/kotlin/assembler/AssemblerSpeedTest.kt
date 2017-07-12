package venus.assembler

import org.junit.Test
import kotlin.test.assertTrue
import venus.simulator.Simulator
import venus.linker.Linker

class AssemblerSpeedTest {
    @Test
    fun nopRepeat() {
        val prog = Assembler.assemble("nop\n".repeat(1000))
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertTrue(true)
    }
}