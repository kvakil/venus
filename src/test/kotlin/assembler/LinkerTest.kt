package venus.linker

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail
import venus.simulator.Simulator
import venus.assembler.Assembler
import venus.assembler.AssemblerError

class LinkerTest {
    @Test
    fun linkOneFile() {
        val prog = Assembler("""
        start:
        addi x8 x8 1
        addi x9 x0 2
        beq x8 x9 skip
        jal x0 start
        skip:
        """).assemble()
        val linked = Linker.link(listOf(prog))
        var sim = Simulator(linked)
        sim.run()
        assertEquals(2, sim.state.getReg(8))
    }

    @Test
    fun linkTwoFiles() {
        val prog1 = Assembler("""
        foo:
            jal x0 bar
            addi x8 x0 8
        """).assemble()
        val prog2 = Assembler("""
        bar:
            addi x8 x8 1
        """).assemble()
        val linked = Linker.link(listOf(prog1, prog2))
        var sim = Simulator(linked)
        sim.run()
        assertEquals(1, sim.state.getReg(8))
    }

    @Test
    fun privateLabel() {
        val prog1 = Assembler("""
        foo:
            jal x0 _bar
            addi x8 x0 8
        """).assemble()
        val prog2 = Assembler("""
        _bar:
            addi x8 x8 1
        """).assemble()

        try {
            Linker.link(listOf(prog1, prog2))
            fail("allowed jump to 'private' label")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }
}