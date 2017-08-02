package venus.riscv.insts.dsl.impls

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.simulator.Simulator

class BTypeImplementation32(private val cond: (Int, Int) -> Boolean) : InstructionImplementation {
    override operator fun invoke(mcode: MachineCode, sim: Simulator) {
        val rs1: Int = mcode[InstructionField.RS1]
        val rs2: Int = mcode[InstructionField.RS2]
        val imm: Int = constructBranchImmediate(mcode)
        val vrs1: Int = sim.getReg(rs1)
        val vrs2: Int = sim.getReg(rs2)
        if (cond(vrs1, vrs2))
            sim.incrementPC(imm)
        else
            sim.incrementPC(mcode.length)
    }
}

private fun constructBranchImmediate(mcode: MachineCode): Int {
    val imm_11 = mcode[InstructionField.IMM_11_B]
    val imm_4_1 = mcode[InstructionField.IMM_4_1]
    val imm_10_5 = mcode[InstructionField.IMM_10_5]
    val imm_12 = mcode[InstructionField.IMM_12]
    var imm = 0
    imm = setBitslice(imm, imm_11, 11, 12)
    imm = setBitslice(imm, imm_4_1, 1, 5)
    imm = setBitslice(imm, imm_10_5, 5, 11)
    imm = setBitslice(imm, imm_12, 12, 13)
    return signExtend(imm, 13)
}
