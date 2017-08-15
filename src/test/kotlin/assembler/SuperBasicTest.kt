package venus.assembler

import venus.linker.Linker
import venus.simulator.Simulator
import kotlin.test.Test
import kotlin.test.assertEquals

class SuperBasicTest {
    @Test fun superBasic() {
        val (prog, _) = Assembler.assemble("""
        addi x8 x8 13
        add x9 x8 x8
        """)
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertEquals(13, sim.getReg(8))
        assertEquals(26, sim.getReg(9))
    }

    @Test fun loadStoreByte() {
        val (prog, _) = Assembler.assemble("""
        addi x8 x0 5
        sb x8 0(sp)
        lb x8 0(sp)
        addi x9 x0 200
        sb x9 -1(sp)
        lb x9 -1(sp)
        """)
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertEquals(5, sim.getReg(8))
        assertEquals(-56, sim.getReg(9))
    }
}
