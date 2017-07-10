package venus.assembler

import org.junit.Test
import kotlin.test.assertEquals

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
        val line = "hi: add x0 x0 x0"
        val (label, args) = Lexer.lexLine(line)
        assertEquals("hi", label)
        assertEquals(listOf("add", "x0", "x0", "x0"), args)
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

}