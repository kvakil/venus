package venus.riscv.insts.dsl.impls

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.simulator.Simulator

class RTypeImplementation32(private inline val eval: (Int, Int) -> Int) : InstructionImplementation {
    override operator fun invoke(mcode: MachineCode, sim: Simulator) {
        val rs1 = mcode[InstructionField.RS1]
        val rs2 = mcode[InstructionField.RS2]
        val rd = mcode[InstructionField.RD]
        val vrs1 = sim.getReg(rs1)
        val vrs2 = sim.getReg(rs2)
        sim.setReg(rd, eval(vrs1, vrs2))
        sim.incrementPC(mcode.length)
    }
}
