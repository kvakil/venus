package venus.assembler

import kotlin.test.Test
import kotlin.test.assertEquals
import venus.linker.Linker
import venus.simulator.Simulator

class AssemblerTest {
    @Test fun assembleLexerTest() {
        val (prog, _) = Assembler.assemble("""
        addi x1 x0 5
        addi x2 x1 5
        add x3 x1 x2
        andi x3 x3 8
        """)
        val sim = Simulator(Linker.link(listOf(prog)))
        sim.run()
        assertEquals(8, sim.getReg(3))
    }

    @Test fun storeLoadTest() {
        val (prog, _) = Assembler.assemble("""
        addi x1 x0 100
        sw x1 60(x0)
        lw x2 -40(x1)
        """)
        val sim = Simulator(Linker.link(listOf(prog)))
        sim.step()
        assertEquals(100, sim.getReg(1))
        sim.step()
        assertEquals(100, sim.getReg(1))
        assertEquals(100, sim.loadWord(60))
        sim.step()
        assertEquals(100, sim.getReg(2))
    }

    @Test fun branchTest() {
        val (prog, _) = Assembler.assemble("""
        add x8 x8 x9
        addi x7 x0 5
        start: add x8 x8 x9
        addi x9 x9 1
        bne x9 x6 start
        """)
        val sim = Simulator(Linker.link(listOf(prog)))
        for (i in 1..17) sim.step()
        assertEquals(10, sim.getReg(8))
    }

    @Test fun otherImmediateTest() {
        val (prog, _) = Assembler.assemble("""
        addi x8 x8 0xf7
        addi x9 x9 0b10001
        """)
        val sim = Simulator(Linker.link(listOf(prog)))
        sim.step()
        assertEquals(0xf7, sim.getReg(8))
        sim.step()
        assertEquals(0b10001, sim.getReg(9))
    }

    @Test fun alignTest() {
        val (prog, _) = Assembler.assemble("""
        .data
        .align 3
        one: # 8-byte aligned
        .byte 1
        .align 3
        two: # 8-byte aligned
        .byte 2
        .align 2
        three: # 4-byte aligned
        .byte 3
        .text
        la a1, one
        la a2, two
        la a3, three
        sub x5, a2, a1  # Should be 8
        sub x6, a3, a2  # Should be 4
        """)
        val sim = Simulator(Linker.link(listOf(prog)))
        sim.run()
        assertEquals(8, sim.getReg(5))
        assertEquals(4, sim.getReg(6))
    }
}
