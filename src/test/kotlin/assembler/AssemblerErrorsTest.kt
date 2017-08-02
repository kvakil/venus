package venus.assembler

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.fail

class AssemblerErrorsTest {
    @Test fun noEmptyException() {
        try {
            Assembler.assemble("")
            assertTrue(true)
        } catch (e: AssemblerError) {
            fail("exception thrown for empty line")
        }
    }

    @Test fun tooFewArguments() {
        try {
            Assembler.assemble("addi x1 x5")
            fail("exception not thrown for too few arguments")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test fun immediateTooLarge() {
        try {
            Assembler.assemble("addi x1 x5 100000000")
            fail("exception not thrown for too large immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test fun immediateTooSmall() {
        try {
            Assembler.assemble("addi x1 x5 -100000000")
            fail("exception not thrown for too small immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test fun immediateNaN() {
        try {
            Assembler.assemble("addi x1 x5 foo")
            fail("exception not thrown for bad immediate")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test fun registerNotARegister() {
        try {
            Assembler.assemble("addi blah x5 1")
            fail("exception not thrown for bad register")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test fun badInstructionTest() {
        try {
            Assembler.assemble("nopi x0 x5 1")
            fail("exception not thrown for bad instruction")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test fun registerIdTooBig() {
        try {
            Assembler.assemble("addi x32 x5 x1")
            fail("exception not thrown for invalid register id")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }

    @Test fun labelTwice() {
        try {
            Assembler.assemble("foo: nop\nfoo: nop")
            fail("exception not thrown for same label twice")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }
}
