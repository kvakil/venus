package venus.simulator.impl

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation

object SWImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val rs2: Int = inst.getField(InstructionField.RS2)
        val imm: Int = constructStoreImmediate(inst)
        state.mem.storeWord(state.getReg(rs1) + imm, rs2)
        state.pc += inst.length
    }
}
