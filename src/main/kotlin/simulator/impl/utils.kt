package venus.simulator.impl

import venus.riscv.Instruction
import venus.riscv.InstructionField

/** Sign extends v of sz bits to a 32 bit integer */
fun signExtend(v: Int, sz: Int): Int = v shl (32 - sz) shr (32 - sz)

/** Returns x[end:start] = y */
fun setBitslice(x: Int, y: Int, start: Int, end: Int): Int {
    val mask: Int = ((1L shl end) - (1L shl start)).inv().toInt()
    return (mask and x) or (y shl start)
}

fun constructBranchImmediate(inst: Instruction): Int {
    val imm_11: Int = inst.getField(InstructionField.IMM_11_B)
    val imm_4_1: Int = inst.getField(InstructionField.IMM_4_1)
    val imm_10_5: Int = inst.getField(InstructionField.IMM_10_5)
    val imm_12: Int = inst.getField(InstructionField.IMM_12)
    var imm: Int = 0
    imm = setBitslice(imm, imm_11, 11, 12)
    imm = setBitslice(imm, imm_4_1, 1, 5)
    imm = setBitslice(imm, imm_10_5, 5, 11)
    imm = setBitslice(imm, imm_12, 12, 13)
    return signExtend(imm, 13)
}