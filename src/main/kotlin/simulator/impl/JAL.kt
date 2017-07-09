package venus.simulator.impl

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.DispatchTest
import venus.simulator.FieldTest
import venus.simulator.InstructionImplementation

object JAL {
    val implementation = object : InstructionImplementation {
        override operator fun invoke(inst: Instruction, state: SimulatorState) {
            val rd: Int = inst.getField(InstructionField.RD)
            val imm: Int = constructJALImmediate(inst)
            state.pc += imm
        }
    }

    val tests: List<DispatchTest> = listOf(
        FieldTest(InstructionField.OPCODE, 0b1101111)
    )
}