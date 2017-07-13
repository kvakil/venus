package venus.assembler

import org.junit.Test
import kotlin.test.assertTrue
import venus.simulator.Simulator
import venus.linker.Linker

class AssemblerSpeedTest {
    @Test
    fun nopRepeat() {
        val prog = Assembler("nop\n".repeat(1000)).assemble()
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertTrue(true)
    }
}