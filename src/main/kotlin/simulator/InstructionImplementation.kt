package venus.simulator

import venus.riscv.Instruction

interface InstructionImplementation {
    operator fun invoke(inst: Instruction, sim: Simulator)
}