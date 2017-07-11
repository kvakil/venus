package venus.assembler

import org.junit.Test
import kotlin.test.assertEquals
import venus.simulator.Simulator

class PseudoTest {
    @Test
    fun moveTest() {
        val prog = Assembler.assemble("""
        addi x1 x0 5
        move x2 x1
        """)
        var sim = Simulator(prog.dump())
        sim.run()
        assertEquals(5, sim.state.getReg(2))
    }
}