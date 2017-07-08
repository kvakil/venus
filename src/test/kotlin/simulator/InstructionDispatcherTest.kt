package venus.simulator

import org.junit.Test
import kotlin.test.assertEquals
import venus.riscv.*

class InstructionDispatcherTest {
    @Test
    fun correctOpcode() {
        // add x3 x1 x2
        val inst = Instruction(0b00000000000100010000000110110011)
        val impl = InstructionDispatcher.dispatch(inst)
        assertEquals(impl, InstructionDispatcher.add)
    }
}