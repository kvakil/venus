package venus.simulator

import org.junit.Test
import kotlin.test.assertEquals
import venus.assembler.Assembler
import venus.assembler.Linker

class FunctionCallTest {
    @Test
    fun doubleJALR() {
        val prog = Assembler.assemble("""
            jal x0 main
        double:
            add a0 a0 a0
            jalr x0 ra 0
        main:
            addi a0 x0 5
            jal ra double
            add x1 a0 x0
        """)
        val linked = Linker.link(listOf(prog))
        var sim = Simulator(linked)
        sim.run()
        assertEquals(10, sim.state.getReg(1))
    }
}