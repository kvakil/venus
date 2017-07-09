package venus.simulator.impl

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.DispatchTest
import venus.simulator.FieldTest
import venus.simulator.InstructionImplementation

object ADDI {
    val implementation = object : InstructionImplementation {
        override operator fun invoke(inst: Instruction, state: SimulatorState) {
            val rs1: Int = inst.getField(InstructionField.RS1)
            val imm: Int = signExtend(inst.getField(InstructionField.IMM_11_0), 12)
            val rd: Int = inst.getField(InstructionField.RD)
            state.setReg(rd, state.getReg(rs1) + imm)
            state.pc += inst.length
        }
    }

    val tests: List<DispatchTest> = listOf(
        FieldTest(InstructionField.OPCODE, 0b0010011),
        FieldTest(InstructionField.FUNCT3, 0b000),
        FieldTest(InstructionField.FUNCT7, 0b0000000)
    )
}