package venus.simulator

import org.junit.Test
import kotlin.test.assertEquals

class MemoryTest {
    @Test
    fun byteStoreLoadTest() {
        val memory = Memory()
        memory.storeByte(100, 42)
        assertEquals(42, memory.loadByte(100))
    }

    @Test
    fun halfwordStoreLoadTest() {
        val memory = Memory()
        memory.storeHalfWord(100, 0xdead)
        assertEquals(0xdead, memory.loadHalfWord(100))
        assertEquals(0xad, memory.loadByte(100))
        assertEquals(0xde, memory.loadByte(101))
    }

    @Test
    fun wordStoreLoadTest() {
        val memory = Memory()
        memory.storeWord(100, 0xdeadbeef.toInt())
        assertEquals(0xdeadbeef.toInt(), memory.loadWord(100))
        assertEquals(0xdead, memory.loadHalfWord(102))
        assertEquals(0xbeef, memory.loadHalfWord(100))
        assertEquals(0xde, memory.loadByte(103))
        assertEquals(0xad, memory.loadByte(102))
        assertEquals(0xbe, memory.loadByte(101))
        assertEquals(0xef, memory.loadByte(100))
    }
}