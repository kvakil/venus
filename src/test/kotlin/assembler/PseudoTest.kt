package venus.assembler

import org.junit.Test
import kotlin.test.assertEquals
import venus.simulator.Simulator

class PseudoTest {
    @Test
    fun moveTest() {
        val prog = Assembler.assemble("""
        addi x1 x0 5
        mv x2 x1
        """)
        var sim = Simulator(prog)
        sim.run()
        assertEquals(5, sim.state.getReg(2))
    }
}