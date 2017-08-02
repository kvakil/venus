package venus.riscv

import kotlin.test.Test
import kotlin.test.assertEquals

class InstructionTest {
    @Test fun correctOpcode() {
        val mcode: MachineCode = MachineCode(0x1ead12aa)
        assertEquals(0x2a, mcode[InstructionField.OPCODE])
    }

    @Test fun setGetFields() {
        val mcode: MachineCode = MachineCode(0x1ead12aa)
        mcode[InstructionField.OPCODE] = 0x1b
        assertEquals(0x1b, mcode[InstructionField.OPCODE])
    }
}
