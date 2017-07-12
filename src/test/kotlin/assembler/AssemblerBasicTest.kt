package venus.assembler

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail
import venus.simulator.Simulator

class AssemblerBasicTest {
    @Test
    fun assembleEasy() {
        val prog = Program()
        try {
            Assembler.addInstruction(prog, listOf("add", "x0", "x0", "x0"))
            assertTrue(true)
        } catch (e: AssemblerError) {
            fail("exception thrown")
        }
    }

    @Test
    fun assembleWithImmediate() {
        val prog = Program()
        try {
            Assembler.addInstruction(prog, listOf("addi", "x1", "x0", "5"))
            assertTrue(true)
        } catch (e: AssemblerError) {
            fail("exception thrown")
        }
    }

    @Test
    fun assembledProgramWorks() {
        val prog = Program()
        try {
            Assembler.addInstruction(prog, listOf("addi", "x1", "x0", "5"))
            Assembler.addInstruction(prog, listOf("andi", "x1", "x1", "4"))
            assertTrue(true)
        } catch (e: AssemblerError) {
            fail("exception thrown")
        }
        val sim = Simulator(prog)
        sim.run()
        assertEquals(4, sim.state.getReg(1))
    }
}