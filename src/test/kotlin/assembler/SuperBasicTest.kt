package venus.assembler

import venus.linker.Linker
import venus.simulator.Simulator
import kotlin.test.Test
import kotlin.test.assertEquals

class SuperBasicTest {
    @Test fun superBasic() {
        val prog = Assembler.assemble("""
        addi x8 x8 13
        add x9 x8 x8
        """)
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertEquals(13, sim.getReg(8))
        assertEquals(26, sim.getReg(9))
    }
}
