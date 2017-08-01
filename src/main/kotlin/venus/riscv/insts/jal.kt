package venus.riscv.insts

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.insts.dsl.JTypeInstruction
import venus.riscv.insts.dsl.setBitslice
import venus.riscv.insts.dsl.signExtend

val jal = JTypeInstruction(
        name = "jal",
        length = 4,
        opcode = 0b1101111,
        eval32 = { mcode, sim ->
            val rd = mcode[InstructionField.RD]
            val imm = constructJALImmediate(mcode)
            sim.setReg(rd, sim.getPC() + mcode.length)
            sim.incrementPC(imm shl 1)
        }
)

private fun constructJALImmediate(mcode: MachineCode): Int {
    val imm_20 = mcode[InstructionField.IMM_20]
    val imm_10_1 = mcode[InstructionField.IMM_10_1]
    val imm_11 = mcode[InstructionField.IMM_11_J]
    val imm_19_12 = mcode[InstructionField.IMM_19_12]
    var imm = 0
    imm = setBitslice(imm, imm_20, 20, 21)
    imm = setBitslice(imm, imm_10_1, 1, 11)
    imm = setBitslice(imm, imm_11, 11, 12)
    imm = setBitslice(imm, imm_19_12, 12, 20)
    return signExtend(imm, 21)
}
