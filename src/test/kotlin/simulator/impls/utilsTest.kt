package venus.simulator.impls

import org.junit.Test
import kotlin.test.assertEquals
import venus.riscv.Instruction

class UtilsTest {
    @Test
    fun testSignExtension() {
        val v: Int = 0b101
        assertEquals(-3, signExtend(v, 3))
    }

    @Test
    fun testSetBitslice() {
        val v: Int = 0b01100101
        assertEquals(0b00111001, setBitslice(v, 0b011100, 1, 7))
    }

    @Test
    fun testSetBitsliceEdge() {
        assertEquals(-2, setBitslice(0, -1, 1, 32))
    }

    @Test
    fun testConstructBranchImmediate() {
        val inst = Instruction(0b10101010000000000000101011100011.toInt())
        assertEquals(-1356, constructBranchImmediate(inst))
        val inst2 = Instruction(0b00101010000000000000101011100011.toInt())
        assertEquals(2740, constructBranchImmediate(inst2))
    }
}