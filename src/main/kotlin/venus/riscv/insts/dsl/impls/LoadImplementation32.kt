package venus.riscv.insts.dsl.impls

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.simulator.Simulator

class LoadImplementation32(private val load: (Simulator, Int) -> Int,
                           private val postLoad: (Int) -> Int) : InstructionImplementation {
    override operator fun invoke(mcode: MachineCode, sim: Simulator) {
        val rs1 = mcode[InstructionField.RS1]
        val rd = mcode[InstructionField.RD]
        val vrs1 = sim.getReg(rs1)
        val imm = signExtend(mcode[InstructionField.IMM_11_0], 12)
        sim.setReg(rd, postLoad(load(sim, vrs1 + imm)))
        sim.incrementPC(mcode.length)
    }
}
