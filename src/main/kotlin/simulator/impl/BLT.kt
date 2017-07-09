package venus.simulator.impl

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.DispatchTest
import venus.simulator.FieldTest
import venus.simulator.InstructionImplementation

object BLT {
    val implementation = object : InstructionImplementation {
        override operator fun invoke(inst: Instruction, state: SimulatorState) {
            val rs1: Int = inst.getField(InstructionField.RS1)
            val rs2: Int = inst.getField(InstructionField.RS2)
            val imm: Int = constructBranchImmediate(inst)
            if (state.getReg(rs1) < state.getReg(rs2))
                state.pc += imm
            else
                state.pc += inst.length
        }
    }

    val tests: List<DispatchTest> = listOf(
        FieldTest(InstructionField.OPCODE, 0b1100011),
        FieldTest(InstructionField.FUNCT3, 0b100)
    )
}