package venus.simulator.impls.types

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.Simulator
import venus.simulator.InstructionImplementation
import venus.simulator.impls.signExtend

abstract class ITypeImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, sim: Simulator) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val imm: Int = signExtend(inst.getField(InstructionField.IMM_11_0), 12)
        val rd: Int = inst.getField(InstructionField.RD)
        val vrs1: Int = sim.state.getReg(rs1)
        sim.setReg(rd, evaluate(vrs1, imm))
        sim.incrementPC(inst.length)
    }

    abstract fun evaluate(vrs1: Int, imm: Int): Int
}
