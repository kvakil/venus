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
        assertEquals(5, sim.getReg(2))
    }

    @Test
    fun liTest() {
        val prog = Assembler.assemble("""
        li x8 2000000000
        li x9 1001
        li x10 3000000005
        li x11 -1234
        """)
        var sim = Simulator(prog)
        sim.run()
        assertEquals(2000000000, sim.getReg(8))
        assertEquals(1001, sim.getReg(9))
        assertEquals(-1294967291, sim.getReg(10))
        assertEquals(-1234, sim.getReg(11))
    }
}