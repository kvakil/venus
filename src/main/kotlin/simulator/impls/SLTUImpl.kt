package venus.simulator.impls

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation

object SLTUImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val rs2: Int = inst.getField(InstructionField.RS2)
        val rd: Int = inst.getField(InstructionField.RD)
        val v1: Int = state.getReg(rs1)
        val v2: Int = state.getReg(rs2)
        if (compareUnsigned(v1, v2) == -1)
            state.setReg(rd, 1)
        else
            state.setReg(rd, 0)
        state.pc += inst.length
    }
}
