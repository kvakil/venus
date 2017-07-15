package venus.simulator.impls.types

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.Simulator
import venus.simulator.InstructionImplementation

abstract class ShiftTypeImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, sim: Simulator) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val shamt: Int = inst.getField(InstructionField.SHAMT)
        val rd: Int = inst.getField(InstructionField.RD)
        sim.setReg(rd, evaluate(sim.state.getReg(rs1), shamt))
        sim.incrementPC(inst.length)
    }

    abstract fun evaluate(vrs1: Int, shamt: Int): Int
}
