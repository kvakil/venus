package venus.simulator

import venus.riscv.Instruction

interface DispatchTest {
    operator fun invoke(inst: Instruction): Boolean
}