package venus

import org.junit.Test
import kotlin.test.assertEquals

class TwoNumbers {
    @Test
    fun canBeAdded() {
        val adder = Adder()
        assertEquals(10, adder.add(5, 5))
    }
}