package venus.simulator.impl

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation

object LWImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val imm: Int = signExtend(inst.getField(InstructionField.IMM_11_0), 12)
        val rd: Int = inst.getField(InstructionField.RD)
        val mem = state.mem.loadWord(rs1 + imm)
        state.setReg(rd, mem)
        state.pc += inst.length
    }
}
