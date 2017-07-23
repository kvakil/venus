package venus.simulator.impls.types

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.Simulator
import venus.simulator.InstructionImplementation
import venus.simulator.impls.constructStoreImmediate

abstract class STypeImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, sim: Simulator) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val rs2: Int = inst.getField(InstructionField.RS2)
        val imm: Int = constructStoreImmediate(inst)
        val addr: Int = sim.getReg(rs1) + imm
        val vrs2: Int = sim.getReg(rs2)
        evaluate(sim, addr, vrs2)
        sim.incrementPC(inst.length)
    }

    abstract fun evaluate(sim: Simulator, addr: Int, vrs2: Int)
}
