package venus.riscv

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
class InstructionFormatTest {
    fun numberOfTrailingZeros(n: Int): Int {
        var mask: Int = 1;
        for (i in 0 until 32) {
            if (n and mask != 0) return i
            mask = mask shl 1
        }
        return 32
    }

    /** Test that numberOfTrailingZeros(it.mask) == it.shift */
    @Test
    fun countTrailingZerosMask() {
        enumValues<InstructionFormat>().forEach {
            assertEquals(numberOfTrailingZeros(it.mask), it.shift)
        }
    }
}