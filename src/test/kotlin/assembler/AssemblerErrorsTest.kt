package venus.assembler

import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.fail

class AssemblerErrorsTest {
    @Test
    fun noEmptyException() {
        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf(""))
            assertTrue(true)
        } catch (e: AssemblerError) {
            fail("exception thrown for empty line")
        }
    }

    @Test
    fun tooFewArguments() {
        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "x1", "x5"))
            fail("exception not thrown for too few arguments")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test
    fun immediateTooLarge() {
        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "x1", "x5", "100000000"))
            fail("exception not thrown for too large immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test
    fun immediateTooSmall() {
        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "x1", "x5", "-100000000"))
            fail("exception not thrown for too small immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test
    fun immediateNaN() {
        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "x1", "x5", "foo"))
            fail("exception not thrown for bad immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test
    fun registerNotARegister() {
        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "blah", "x5", "1"))
            fail("exception not thrown for bad register")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test
    fun badInstructionTest() {
        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("nopi", "x0", "x5", "1"))
            fail("exception not thrown for bad instruction")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test
    fun registerIdTooBig() {
        try {
            val prog = Program()
            Assembler.addInstruction(prog, listOf("addi", "x32", "x5", "1"))
            fail("exception not thrown for bad instruction")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }
}