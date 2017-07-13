package venus.assembler

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class LexerTest {
    @Test
    fun basicLexing() {
        val line = "add x0 x0 x0"
        val (label, args) = Lexer.lexLine(line)
        assertEquals("", label)
        assertEquals(listOf("add", "x0", "x0", "x0"), args)
    }

    @Test
    fun lexLabel() {
        val line = "start: add x2 x2 x3"
        val (label, args) = Lexer.lexLine(line)
        assertEquals("start", label)
        assertEquals(listOf("add", "x2", "x2", "x3"), args)
    }

    @Test
    fun lexComment() {
        val line = "add x0 x0 x0 # hi: x0"
        val (label, args) = Lexer.lexLine(line)
        assertEquals("", label)
        assertEquals(listOf("add", "x0", "x0", "x0"), args)
    }

    @Test
    fun lexComma() {
        val line = "add x0, x1, x2"
        val (label, args) = Lexer.lexLine(line)
        assertEquals("", label)
        assertEquals(listOf("add", "x0", "x1", "x2"), args)
    }

    @Test
    fun lexLabelSpace() {
        val line = "  \t  start: add x0, x1, x2"
        val (label, args) = Lexer.lexLine(line)
        assertEquals("start", label)
        assertEquals(listOf("add", "x0", "x1", "x2"), args)
    }

    @Test
    fun lexBaseDisplacement() {
        val line = "sw x1 0(x2)"
        val (label, args) = Lexer.lexLine(line)
        assertEquals("", label)
        assertEquals(listOf("sw", "x1", "0", "x2"), args)
    }

    @Test
    fun lexNothing() {
        val line = ""
        val (label, args) = Lexer.lexLine(line)
        assertEquals("", label)
        assertEquals(listOf(""), args)
    }

    @Test
    fun lexAsciizBadStrings() {
        try {
            Assembler("""
            .data
            .asciiz   unquoted
            .text
            nop
            """).assemble()
            fail("didn't error on unquoted")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            Assembler("""
            .data
            .asciiz   "no end quote
            .text
            nop
            """).assemble()
            fail("didn't error on missing end quote")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }

        try {
            Assembler("""
            .data
            .asciiz   "good" junk
            .text
            nop
            """).assemble()
            fail("didn't error on junk")
        } catch (e: AssemblerError) {
            assertTrue(true)
        }
    }
}