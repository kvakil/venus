package venus.simulator.impls

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.simulator.Simulator
import venus.simulator.InstructionImplementation

object JALImpl : InstructionImplementation {
    override operator fun invoke(inst: Instruction, sim: Simulator) {
        val rd: Int = inst.getField(InstructionField.RD)
        val imm: Int = constructJALImmediate(inst)
        sim.setReg(rd, sim.getPC() + inst.length)
        sim.incrementPC(imm shl 1)
    }
}
