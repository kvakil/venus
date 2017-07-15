package venus.simulator.impls

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.Simulator
import venus.simulator.InstructionImplementation

object AUIPCImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, sim: Simulator) {
        val rd: Int = inst.getField(InstructionField.RD)
        val imm: Int = inst.getField(InstructionField.IMM_31_12) shl 12
        sim.setReg(rd, sim.getPC() + imm)
        sim.incrementPC(inst.length)
    }
}
