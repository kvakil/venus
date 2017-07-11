package venus.simulator.impls

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation

object JALImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        val rd: Int = inst.getField(InstructionField.RD)
        val imm: Int = constructJALImmediate(inst)
        state.setReg(rd, state.pc + inst.length)
        state.pc += imm shl 1
    }
}
