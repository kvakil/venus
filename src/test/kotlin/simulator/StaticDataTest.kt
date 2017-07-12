package venus.simulator

import org.junit.Test
import kotlin.test.assertEquals
import venus.assembler.Assembler
import venus.linker.Linker
import venus.riscv.MemorySegments

class StaticDataTest {
    @Test
    fun easyManualLoad() {
        val prog = Assembler.assemble("""
        .data
        .byte 1 2 3 4
        .text
        nop
        """)
        val linked = Linker.link(listOf(prog))
        var sim = Simulator(linked)
        assertEquals(1, sim.state.mem.loadByte(MemorySegments.STATIC_BEGIN))
        assertEquals(2, sim.state.mem.loadByte(MemorySegments.STATIC_BEGIN + 1))
        assertEquals(3, sim.state.mem.loadByte(MemorySegments.STATIC_BEGIN + 2))
        assertEquals(4, sim.state.mem.loadByte(MemorySegments.STATIC_BEGIN + 3))
    }
}