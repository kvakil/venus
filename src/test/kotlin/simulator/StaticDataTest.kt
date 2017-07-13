package venus.simulator

import org.junit.Test
import kotlin.test.assertEquals
import venus.assembler.Assembler
import venus.linker.Linker
import venus.riscv.MemorySegments

class StaticDataTest {
    @Test
    fun easyManualLoad() {
        val prog = Assembler("""
        .data
        .byte 1 2 3 4
        .text
        nop
        """).assemble()
        val linked = Linker.link(listOf(prog))
        var sim = Simulator(linked)
        assertEquals(1, sim.state.mem.loadByte(MemorySegments.STATIC_BEGIN))
        assertEquals(2, sim.state.mem.loadByte(MemorySegments.STATIC_BEGIN + 1))
        assertEquals(3, sim.state.mem.loadByte(MemorySegments.STATIC_BEGIN + 2))
        assertEquals(4, sim.state.mem.loadByte(MemorySegments.STATIC_BEGIN + 3))
    }

    @Test
    fun asciizManualLoad() {
        val expected = """This ' is a \"string\"!"""
        val prog = Assembler("""
        .data
        .asciiz   "${expected}"
        .text
        nop
        """).assemble()
        val linked = Linker.link(listOf(prog))
        var sim = Simulator(linked)
        var offset = MemorySegments.STATIC_BEGIN
        for (c in expected) {
            assertEquals(c.toInt(), sim.state.mem.loadByte(offset))
            offset++
        }
    }

    @Test
    fun asciizNulTerminated() {
        val prog = Assembler("""
        .data
        .asciiz "a"
        .asciiz "b"
        """).assemble()
        val linked = Linker.link(listOf(prog))
        var sim = Simulator(linked)
        var offset = MemorySegments.STATIC_BEGIN
        assertEquals('a'.toInt(), sim.state.mem.loadByte(offset))
        assertEquals(0, sim.state.mem.loadByte(offset + 1))
        assertEquals('b'.toInt(), sim.state.mem.loadByte(offset + 2))
        assertEquals(0, sim.state.mem.loadByte(offset + 3))
    }

    @Test
    fun linkedStaticBytes() {
        val prog1 = Assembler("""
        .data
        .byte 1
        """).assemble()
        val prog2 = Assembler("""
        .data
        .byte 2
        """).assemble()
        val linked = Linker.link(listOf(prog1, prog2))
        var sim = Simulator(linked)
        var offset = MemorySegments.STATIC_BEGIN
        assertEquals(1, sim.state.mem.loadByte(offset))
        assertEquals(2, sim.state.mem.loadByte(offset + 1))
    }
}