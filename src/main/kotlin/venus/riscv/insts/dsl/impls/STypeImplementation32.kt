package venus.riscv.insts.dsl.impls

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.simulator.Simulator

class STypeImplementation32(private val store: (Simulator, Int, Int) -> Unit) : InstructionImplementation {
    override operator fun invoke(mcode: MachineCode, sim: Simulator) {
        val rs1 = mcode[InstructionField.RS1]
        val rs2 = mcode[InstructionField.RS2]
        val imm = constructStoreImmediate(mcode)
        val vrs1 = sim.getReg(rs1)
        val vrs2 = sim.getReg(rs2)
        store(sim, vrs1 + imm, vrs2)
        sim.incrementPC(mcode.length)
    }
}

private fun constructStoreImmediate(mcode: MachineCode): Int {
    val imm_11_5 = mcode[InstructionField.IMM_11_5]
    val imm_4_0 = mcode[InstructionField.IMM_4_0]
    var imm = 0
    imm = setBitslice(imm, imm_11_5, 5, 12)
    imm = setBitslice(imm, imm_4_0, 0, 5)
    return signExtend(imm, 12)
}
