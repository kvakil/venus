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
        val sim = Simulator(prog.dump())
        sim.run()
        assertEquals(4, sim.state.getReg(1))
    }

    @Test
    fun assemblerErrors() {
        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf(""))
            assertTrue(true)
        } catch (e: AssemblerError) {
            fail("exception thrown for empty line")
        }

        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "x1", "x5"))
            fail("exception not thrown for too few arguments")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "x1", "x5", "100000000"))
            fail("exception not thrown for too large immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "x1", "x5", "-100000000"))
            fail("exception not thrown for too small immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "x1", "x5", "foo"))
            fail("exception not thrown for bad immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "blah", "x5", "1"))
            fail("exception not thrown for bad register")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("nopi", "x0", "x5", "1"))
            fail("exception not thrown for bad instruction")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }
}