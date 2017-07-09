package venus.simulator.impl

import org.junit.Test
import kotlin.test.assertEquals
import venus.riscv.Instruction
import venus.simulator.SimulatorState

class ADDTest {
    @Test
    fun basicADD() {
        // add x3 x2 x1
        val inst = Instruction(0b00000000000100010000000110110011)
        val state = SimulatorState()
        state.setReg(1, 10)
        state.setReg(2, 20)
        ADDImpl(inst, state)
        assertEquals(30, state.getReg(3))
    }

    @Test
    fun overflowADD() {
        // add x3 x2 x1
        val inst = Instruction(0b00000000000100010000000110110011)
        val state = SimulatorState()
        state.setReg(1, 2000000000)
        state.setReg(2, 2000000000)
        ADDImpl(inst, state)
        assertEquals(-294967296, state.getReg(3))
    }
}