package venus.simulator.impl

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation

object XORImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val rs2: Int = inst.getField(InstructionField.RS2)
        val rd: Int = inst.getField(InstructionField.RD)
        state.setReg(rd, state.getReg(rs1) xor state.getReg(rs2))
        state.pc += inst.length
    }
}
