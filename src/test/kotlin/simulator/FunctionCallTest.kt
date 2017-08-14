package venus.simulator

import kotlin.test.Test
import kotlin.test.assertEquals
import venus.assembler.Assembler
import venus.linker.Linker

class FunctionCallTest {
    @Test fun doubleJALR() {
        val (prog, _) = Assembler.assemble("""
            jal x0 main
        double:
            add a0 a0 a0
            jalr x0 ra 0
        main:
            addi a0 x0 5
            jal ra double
            add x1 a0 x0
        """)
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertEquals(10, sim.getReg(1))
    }

    @Test fun nestedJALR() {
        val (prog, _) = Assembler.assemble("""
            jal x0 main
        foo:
            addi s0 s0 1
            jalr x0 ra 0
        bar:
            addi sp sp -4
            sw ra 0(sp)
            addi s0 s0 2
            jal ra foo
            lw ra 0(sp)
            addi sp sp 4
            jalr x0 ra 0
        main:
            addi s0 s0 4
            addi sp sp 1000
            jal ra bar
        """)
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertEquals(7, sim.getReg(8))
    }

    @Test fun nestedPseudoJumps() {
        val (prog, _) = Assembler.assemble("""
            j main
        foo:
            addi s0 s0 1
            ret
        bar:
            addi sp sp -4
            sw ra 0(sp)
            addi s0 s0 2
            jal ra foo
            lw ra 0(sp)
            addi sp sp 4
            ret
        main:
            addi s0 s0 4
            addi sp sp 1000
            jal ra bar
        """)
        val linked = Linker.link(listOf(prog))
        val sim = Simulator(linked)
        sim.run()
        assertEquals(7, sim.getReg(8))
    }
}
