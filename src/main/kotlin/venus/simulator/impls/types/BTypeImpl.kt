package venus.simulator.impls.types

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.simulator.InstructionImplementation
import venus.simulator.Simulator
import venus.simulator.impls.constructBranchImmediate

abstract class BTypeImpl : InstructionImplementation {
    override operator fun invoke(inst: MachineCode, sim: Simulator) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val rs2: Int = inst.getField(InstructionField.RS2)
        val imm: Int = constructBranchImmediate(inst)
        val vrs1: Int = sim.getReg(rs1)
        val vrs2: Int = sim.getReg(rs2)
        if (evaluate(vrs1, vrs2))
            sim.incrementPC(imm)
        else
            sim.incrementPC(inst.length)
    }

    abstract fun evaluate(vrs1: Int, vrs2: Int): Boolean
}
