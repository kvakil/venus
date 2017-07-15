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
        var sim = Simulator(prog)
        sim.run()
        assertEquals(8, sim.getReg(3))
    }

    @Test
    fun storeLoadTest() {
        val prog = Assembler.assemble("""
        addi x1 x0 100
        sw 60(x0) x1
        lw x2 -40(x1)
        """)
        var sim = Simulator(prog)
        sim.step()
        assertEquals(100, sim.getReg(1))
        sim.step()
        assertEquals(100, sim.getReg(1))
        assertEquals(100, sim.state.mem.loadWord(60))
        sim.step()
        assertEquals(100, sim.getReg(2))
    }

    @Test
    fun branchTest() {
        val prog = Assembler.assemble("""
        add x8 x8 x9
        addi x7 x0 5
        start: add x8 x8 x9
        addi x9 x9 1
        bne x9 x6 start
        """)
        var sim = Simulator(prog)
        for (i in 1..17) sim.step()
        assertEquals(10, sim.getReg(8))
    }
}