package venus.simulator.impl

import org.junit.Test
import kotlin.test.assertEquals

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
}