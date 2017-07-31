package venus.riscv

import kotlin.test.Test
import kotlin.test.assertEquals

class InstructionTest {
    @Test fun correctOpcode() {
        val inst: Instruction = Instruction(0x1ead12aa)
        assertEquals(0x2a, inst.getField(InstructionField.OPCODE))
    }

    @Test fun setGetFields() {
        val inst: Instruction = Instruction(0x1ead12aa)
        inst.setField(InstructionField.OPCODE, 0x1b)
        assertEquals(0x1b, inst.getField(InstructionField.OPCODE))
    }
}
