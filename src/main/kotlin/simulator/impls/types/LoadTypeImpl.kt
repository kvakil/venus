package venus.simulator.impls.types

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation
import venus.simulator.Memory
import venus.simulator.impls.signExtend

abstract class LoadTypeImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val imm: Int = signExtend(inst.getField(InstructionField.IMM_11_0), 12)
        val rd: Int = inst.getField(InstructionField.RD)
        val vrs1: Int = state.getReg(rs1)
        state.setReg(rd, evaluate(state.mem, vrs1, imm))
        state.pc += inst.length
    }

    abstract fun evaluate(mem: Memory, vrs1: Int, imm: Int): Int
}
