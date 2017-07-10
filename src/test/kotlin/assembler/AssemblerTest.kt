package venus.assembler

import org.junit.Test
import kotlin.test.assertEquals
import venus.simulator.Simulator

class AssemblerTest {
    @Test
    fun assembleLexerTest() {
        val prog = Assembler.assemble("""
        addi x1 x0 5
        addi x2 x1 5
        add x3 x1 x2
        andi x3 x3 8
        """)
        var sim = Simulator(prog.dump())
        sim.run()
        assertEquals(8, sim.state.getReg(3))
    }
}