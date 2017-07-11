package venus.assembler

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import venus.simulator.Simulator

class LinkerTest {
    @Test
    fun linkOneFile() {
        val prog = Assembler.assemble("""
        start:
        addi x8 x8 1
        addi x9 x0 2
        beq x8 x9 skip
        jal x0 start
        skip:
        """)
        val linked = Linker.link(listOf(prog))
        var sim = Simulator(linked.dump())
        sim.run()
        assertEquals(2, sim.state.getReg(8))
    }
}