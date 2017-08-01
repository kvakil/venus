package venus.simulator

import venus.riscv.MachineCode
import kotlin.test.Test
import kotlin.test.assertEquals

class InstructionDispatcherTest {
    @Test fun correctDispatchAdd() {
        // add x3 x1 x2
        val inst = MachineCode(0b00000000000100010000000110110011)
        val impl = InstructionDispatcher.dispatch(inst)
        assertEquals(impl, InstructionDispatcher.add.implementation)
    }
}
