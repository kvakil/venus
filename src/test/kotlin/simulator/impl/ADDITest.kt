package venus.simulator.impl

import org.junit.Test
import kotlin.test.assertEquals
import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.DispatchTest
import venus.simulator.FieldTest
import venus.simulator.InstructionImplementation

class ADDITest {
    @Test
    fun basicADDI() {
        // addi x3 x2 1
        val inst = Instruction(0b00000000000100010000000110110011)
        val state = SimulatorState()
        state.setReg(2, 10)
        ADDI.implementation(inst, state)
        assertEquals(11, state.getReg(3))
    }

    @Test
    fun negativeADDI() {
        // addi x3 x2 -1
        val inst = Instruction(0b11111111111100010000000110110011.toInt())
        val state = SimulatorState()
        state.setReg(2, 10)
        ADDI.implementation(inst, state)
        assertEquals(9, state.getReg(3))
    }

    @Test
    fun almostNegativeADDI() {
        // addi x3 x2 2047
        val inst = Instruction(0b01111111111100010000000110110011)
        val state = SimulatorState()
        state.setReg(2, 10)
        ADDI.implementation(inst, state)
        assertEquals(2057, state.getReg(3))
    }
}