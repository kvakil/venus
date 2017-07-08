package venus.simulator

import venus.riscv.Instruction

interface DispatchTest {
    fun matches(inst: Instruction): Boolean
}