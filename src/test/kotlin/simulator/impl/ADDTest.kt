package venus.simulator.impl

import org.junit.Test
import kotlin.test.assertEquals
import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.DispatchTest
import venus.simulator.FieldTest
import venus.simulator.InstructionImplementation

class ADDTest {
    @Test
    fun basicADD() {
        val inst = Instruction(0b00000000000100010000000110110011) // add x3 x1 x2
        val state = SimulatorState()
        state.setReg(1, 10)
        state.setReg(2, 20)
        ADD.implementation(inst, state)
        assertEquals(30, state.getReg(3))
    }

    @Test
    fun overflowADD() {
        val inst = Instruction(0b00000000000100010000000110110011) // add x3 x1 x2
        val state = SimulatorState()
        state.setReg(1, 2000000000)
        state.setReg(2, 2000000000)
        ADD.implementation(inst, state)
        assertEquals(-294967296, state.getReg(3))
    }
}