package venus.simulator.impls.types

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.simulator.InstructionImplementation
import venus.simulator.Simulator

abstract class ShiftTypeImpl : InstructionImplementation {
    override operator fun invoke(inst: MachineCode, sim: Simulator) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val shamt: Int = inst.getField(InstructionField.SHAMT)
        val rd: Int = inst.getField(InstructionField.RD)
        sim.setReg(rd, evaluate(sim.getReg(rs1), shamt))
        sim.incrementPC(inst.length)
    }

    abstract fun evaluate(vrs1: Int, shamt: Int): Int
}
