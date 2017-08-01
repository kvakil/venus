package venus.simulator.impls

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.simulator.InstructionImplementation
import venus.simulator.Simulator

object LUIImpl : InstructionImplementation {
    override operator fun invoke(inst: MachineCode, sim: Simulator) {
        val rd: Int = inst.getField(InstructionField.RD)
        val imm: Int = inst.getField(InstructionField.IMM_31_12) shl 12
        sim.setReg(rd, imm)
        sim.incrementPC(inst.length)
    }
}
