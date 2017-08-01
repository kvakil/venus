package venus.simulator.impls

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.simulator.InstructionImplementation
import venus.simulator.Simulator

object JALImpl : InstructionImplementation {
    override operator fun invoke(inst: MachineCode, sim: Simulator) {
        val rd: Int = inst.getField(InstructionField.RD)
        val imm: Int = constructJALImmediate(inst)
        sim.setReg(rd, sim.getPC() + inst.length)
        sim.incrementPC(imm shl 1)
    }
}
