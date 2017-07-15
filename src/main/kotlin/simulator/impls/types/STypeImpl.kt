package venus.simulator.impls.types

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.SimulatorState
import venus.simulator.InstructionImplementation
import venus.simulator.Memory
import venus.simulator.impls.constructStoreImmediate

abstract class STypeImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, state: SimulatorState) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val rs2: Int = inst.getField(InstructionField.RS2)
        val imm: Int = constructStoreImmediate(inst)
        val addr: Int = state.getReg(rs1) + imm
        val vrs2: Int = state.getReg(rs2)
        evaluate(state.mem, addr, vrs2)
        state.pc += inst.length
    }

    abstract fun evaluate(mem: Memory, addr: Int, vrs2: Int)
}
