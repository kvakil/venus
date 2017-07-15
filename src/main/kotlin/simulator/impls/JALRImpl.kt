package venus.simulator.impls

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.Simulator
import venus.simulator.InstructionImplementation

object JALRImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, sim: Simulator) {
        val rs1: Int = inst.getField(InstructionField.RS1)
        val imm: Int = signExtend(inst.getField(InstructionField.IMM_11_0), 12)
        val rd: Int = inst.getField(InstructionField.RD)
        sim.setReg(rd, sim.getPC() + inst.length)
        sim.setPC(((sim.getReg(rs1) + imm) shr 1) shl 1)
    }
}
