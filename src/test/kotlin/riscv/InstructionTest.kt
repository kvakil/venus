package venus.riscv

import org.junit.Test
import kotlin.test.assertEquals

class InstructionTest {
    @Test
    fun correctOpcode() {
        val inst: Instruction = Instruction(0x1ead12aa)
        assertEquals(0x2a, inst.getField(InstructionFormat.OPCODE))
    }
}