package venus.simulator.impl

import org.junit.Test
import kotlin.test.assertEquals
import venus.riscv.Instruction
import venus.simulator.SimulatorState

class SLTUTest {
    @Test
    fun basicSLTU() {
        // sltu x3 x2 x1
        val inst = Instruction(0b00000000000100010011000110110011)
        val state = SimulatorState()
        state.setReg(2, 10)
        state.setReg(1, 20)
        SLTUImpl(inst, state)
        assertEquals(1, state.getReg(3))

        state.setReg(2, 30)
        state.setReg(1, 20)
        SLTUImpl(inst, state)
        assertEquals(0, state.getReg(3))
    }

    @Test
    fun equalSLTU() {
        // sltu x3 x2 x1
        val inst = Instruction(0b00000000000100010011000110110011)
        val state = SimulatorState()
        state.setReg(2, 0)
        state.setReg(1, 0)
        SLTUImpl(inst, state)
        assertEquals(0, state.getReg(3))

        state.setReg(2, -3)
        state.setReg(1, -3)
        SLTUImpl(inst, state)
        assertEquals(0, state.getReg(3))
    }

    @Test
    fun unsignedSLTU() {
        // sltu x3 x2 x1
        val inst = Instruction(0b00000000000100010011000110110011)
        val state = SimulatorState()
        state.setReg(2, 1000)
        state.setReg(1, -1)
        SLTUImpl(inst, state)
        assertEquals(1, state.getReg(3))

        state.setReg(2, -1)
        state.setReg(1, 1000)
        SLTUImpl(inst, state)
        assertEquals(0, state.getReg(3))

        state.setReg(2, 0)
        state.setReg(1, 1)
        SLTUImpl(inst, state)
        assertEquals(1, state.getReg(3))

        state.setReg(2, 1)
        state.setReg(1, 0)
        SLTUImpl(inst, state)
        assertEquals(0, state.getReg(3))
    }
}