package venus.assembler

import kotlin.test.Test
import kotlin.test.assertTrue

class AssemblerErrorsTest {
    @Test fun noEmptyException() {
        val (_, errors) = Assembler.assemble("")
        assertTrue(errors.isEmpty())
    }

    @Test fun tooFewArguments() {
        val (_, errors) = Assembler.assemble("addi x1 x5")
        assertTrue(errors.isNotEmpty())
    }

    @Test fun immediateTooLarge() {
        val (_, errors) = Assembler.assemble("addi x1 x5 100000000")
        assertTrue(errors.isNotEmpty())
    }

    @Test fun immediateTooSmall() {
        val (_, errors) = Assembler.assemble("addi x1 x5 -100000000")
        assertTrue(errors.isNotEmpty())
    }

    @Test fun immediateNaN() {
        val (_, errors) = Assembler.assemble("addi x1 x5 foo")
        assertTrue(errors.isNotEmpty())
    }

    @Test fun registerNotARegister() {
        val (_, errors) = Assembler.assemble("addi blah x5 1")
        assertTrue(errors.isNotEmpty())
    }

    @Test fun badInstructionTest() {
        val (_, errors) = Assembler.assemble("nopi x0 x5 1")
        assertTrue(errors.isNotEmpty())
    }

    @Test fun registerIdTooBig() {
        val (_, errors) = Assembler.assemble("addi x32 x5 x1")
        assertTrue(errors.isNotEmpty())
    }

    @Test fun labelTwice() {
        val (_, errors) = Assembler.assemble("foo: nop\nfoo: nop")
        assertTrue(errors.isNotEmpty())
    }
}
