package venus.simulator.impls

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.simulator.InstructionImplementation
import venus.simulator.Simulator

object JALRImpl : InstructionImplementation {
    override operator fun invoke(inst: MachineCode, sim: Simulator) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val imm: Int = signExtend(inst.getField(InstructionField.IMM_11_0), 12)
        val rd: Int = inst.getField(InstructionField.RD)
        sim.setReg(rd, sim.getPC() + inst.length)
        sim.setPC(((sim.getReg(rs1) + imm) shr 1) shl 1)
    }
}
