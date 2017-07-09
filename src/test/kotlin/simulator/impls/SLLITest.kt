package venus.simulator.impls

import org.junit.Test
import kotlin.test.assertEquals
import venus.riscv.Instruction
import venus.simulator.SimulatorState

class SLLITest {
    @Test
    fun basicSLLI() {
        // sll x1 x2 2
        val inst = Instruction(0b00000000001000010001000010110011)
        val state = SimulatorState()
        state.setReg(2, 20)
        SLLIImpl(inst, state)
        assertEquals(80, state.getReg(1))
    }
}