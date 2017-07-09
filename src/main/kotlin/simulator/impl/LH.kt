package venus.simulator.impl

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.DispatchTest
import venus.simulator.FieldTest
import venus.simulator.InstructionImplementation

object LH {
    val implementation = object : InstructionImplementation {
        override operator fun invoke(inst: Instruction, state: SimulatorState) {
            val rs1: Int = inst.getField(InstructionField.RS1)
            val imm: Int = signExtend(inst.getField(InstructionField.IMM_11_0), 12)
            val rd: Int = inst.getField(InstructionField.RD)
            val mem = signExtend(state.mem.loadHalfWord(rs1 + imm), 16)
            state.setReg(rd, mem)
            state.pc += inst.length
        }
    }

    val tests: List<DispatchTest> = listOf(
        FieldTest(InstructionField.OPCODE, 0b0000011),
        FieldTest(InstructionField.FUNCT3, 0b001)
    )
}