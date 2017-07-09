package venus.simulator

import org.junit.Test
import kotlin.test.assertEquals
import venus.riscv.Instruction

class SimulatorTest {
    @Test
    fun basicRun() {
        val prog: List<Instruction> = listOf(
            // addi x7 x0 5
            Instruction(0b000000000101_00000_000_00111_0010011),
            // start: add x5 x5 x6
            Instruction(0b0000000_00110_00101_000_00101_0110011),
            // addi x6 x6 1
            Instruction(0b000000000001_00110_000_00110_0010011),
            // bne x1 x2 start
            Instruction(0b1111111_00110_00111_001_11001_1100011.toInt())
        )
        val sim = Simulator(prog)
        sim.run()
        assertEquals(10, sim.state.getReg(5))
    }
}