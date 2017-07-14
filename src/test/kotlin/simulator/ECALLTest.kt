package venus.simulator

import org.junit.Test
import kotlin.test.assertEquals
import venus.assembler.Assembler
import venus.linker.Linker

class ECALLTest {
    @Test
    fun goBears() {
        val prog = Assembler.assemble("""
        addi a0 x0 10
        ecall
        addi a0 x0 5""")
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertEquals(10, sim.state.getReg(10))
    }
}