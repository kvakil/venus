package venus.simulator.impl

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.DispatchTest
import venus.simulator.FieldTest
import venus.simulator.InstructionImplementation

object XOR {
    val implementation = object: InstructionImplementation {
        override operator fun invoke(inst: Instruction, state: SimulatorState) {
            val rs1: Int = inst.getField(InstructionField.RS1)
            val rs2: Int = inst.getField(InstructionField.RS2)
            val rd: Int = inst.getField(InstructionField.RD)
            state.setReg(rd, state.getReg(rs1) xor state.getReg(rs2))
            state.pc += 4
        }
    }

    val tests: List<DispatchTest> = listOf(
        FieldTest(InstructionField.OPCODE, 0b0110011),
        FieldTest(InstructionField.FUNCT3, 0b100),
        FieldTest(InstructionField.FUNCT7, 0b0000000)
    )
}