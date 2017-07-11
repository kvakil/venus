package venus.assembler

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail
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

    @Test
    fun linkTwoFiles() {
        val prog1 = Assembler.assemble("""
        foo:
            jal x0 bar
            addi x8 x0 8
        """)
        val prog2 = Assembler.assemble("""
        bar:
            addi x8 x8 1
        """)
        val linked = Linker.link(listOf(prog1, prog2))
        var sim = Simulator(linked.dump())
        sim.run()
        assertEquals(1, sim.state.getReg(8))
    }

    @Test
    fun privateLabel() {
        val prog1 = Assembler.assemble("""
        foo:
            jal x0 _bar
            addi x8 x0 8
        """)
        val prog2 = Assembler.assemble("""
        _bar:
            addi x8 x8 1
        """)

        try {
            Linker.link(listOf(prog1, prog2))
            fail("allowed jump to 'private' label")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }
}