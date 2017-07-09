package venus.assembler

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail
import venus.simulator.Simulator

class AssemblerBasicTest {
    @Test
    fun assembleEasy() {
        val asm = Assembler()
        try {
            asm.addInstruction(listOf("add", "x0", "x0", "x0"))
            assertTrue(true)
        } catch (e: AssemblerError) {
            fail("exception thrown")
        }
    }

    @Test
    fun assembleWithImmediate() {
        val asm = Assembler()
        try {
            asm.addInstruction(listOf("addi", "x1", "x0", "5"))
            assertTrue(true)
        } catch (e: AssemblerError) {
            fail("exception thrown")
        }
    }

    @Test
    fun assembledProgramWorks() {
        val asm = Assembler()
        try {
            asm.addInstruction(listOf("addi", "x1", "x0", "5"))
            asm.addInstruction(listOf("andi", "x1", "x1", "4"))
            assertTrue(true)
        } catch (e: AssemblerError) {
            fail("exception thrown")
        }
        val sim = Simulator(asm.prog.dump())
        sim.run()
        assertEquals(4, sim.state.getReg(1))
    }

    @Test
    fun assemblerErrors() {
        val asm = Assembler()

        try {
            asm.addInstruction(listOf(""))
            fail("exception not thrown for empty line")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            asm.addInstruction(listOf("addi", "x1", "x5"))
            fail("exception not thrown for too few arguments")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            asm.addInstruction(listOf("addi", "x1", "x5", "100000000"))
            fail("exception not thrown for too large immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            asm.addInstruction(listOf("addi", "x1", "x5", "-100000000"))
            fail("exception not thrown for too small immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            asm.addInstruction(listOf("addi", "x1", "x5", "foo"))
            fail("exception not thrown for bad immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            asm.addInstruction(listOf("addi", "blah", "x5", "1"))
            fail("exception not thrown for bad register")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            asm.addInstruction(listOf("nopi", "x0", "x5", "1"))
            fail("exception not thrown for bad instruction")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }
}