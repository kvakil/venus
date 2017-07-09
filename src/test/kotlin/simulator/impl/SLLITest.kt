package venus.simulator.impl

import org.junit.Test
import kotlin.test.assertEquals
import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.DispatchTest
import venus.simulator.FieldTest
import venus.simulator.InstructionImplementation

class SLLITest {
    @Test
    fun basicSLLI() {
        // sll x1 x2 2
        val inst = Instruction(0b00000000001000010001000010110011)
        val state = SimulatorState()
        state.setReg(2, 20)
        SLLI.implementation(inst, state)
        assertEquals(80, state.getReg(1))
    }
}