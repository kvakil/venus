package venus.simulator.impls.types

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation

abstract class BTypeImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val rs2: Int = inst.getField(InstructionField.RS2)
        val imm: Int = constructBranchImmediate(inst)
        val vrs1: Int = state.getReg(rs1)
        val vrs2: Int = state.getReg(rs2)
        if (evaluate(vrs1, vrs2))
            state.pc += imm
        else
            state.pc += inst.length
    }

    abstract fun evaluate(vrs1: Int, vrs2: Int): Boolean
}
