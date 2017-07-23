package venus.simulator.impls

import venus.riscv.Instruction
import venus.riscv.InstructionField

/** Sign extends v of sz bits to a 32 bit integer */
fun signExtend(v: Int, sz: Int): Int = v shl (32 - sz) shr (32 - sz)

fun compareUnsigned(v1: Int, v2: Int): Int {
    if (v1 == v2) return 0
    if (0 <= v1 && v1 < v2)
        return -1
    else if (v2 < 0 && v2 < v1)
        return -1
    else
        return 1
}

/** Returns x[end:start] = y */
fun setBitslice(x: Int, y: Int, start: Int, end: Int): Int {
    val mask: Int = ((1L shl end) - (1L shl start)).inv().toInt()
    return (mask and x) or (y shl start)
}

fun constructBranchImmediate(inst: Instruction): Int {
    val imm_11 = inst.getField(InstructionField.IMM_11_B)
    val imm_4_1 = inst.getField(InstructionField.IMM_4_1)
    val imm_10_5 = inst.getField(InstructionField.IMM_10_5)
    val imm_12 = inst.getField(InstructionField.IMM_12)
    var imm = 0
    imm = setBitslice(imm, imm_11, 11, 12)
    imm = setBitslice(imm, imm_4_1, 1, 5)
    imm = setBitslice(imm, imm_10_5, 5, 11)
    imm = setBitslice(imm, imm_12, 12, 13)
    return signExtend(imm, 13)
}

fun constructJALImmediate(inst: Instruction): Int {
    val imm_20 = inst.getField(InstructionField.IMM_20)
    val imm_10_1 = inst.getField(InstructionField.IMM_10_1)
    val imm_11 = inst.getField(InstructionField.IMM_11_J)
    val imm_19_12 = inst.getField(InstructionField.IMM_19_12)
    var imm = 0
    imm = setBitslice(imm, imm_20, 20, 21)
    imm = setBitslice(imm, imm_10_1, 1, 11)
    imm = setBitslice(imm, imm_11, 11, 12)
    imm = setBitslice(imm, imm_19_12, 12, 20)
    return signExtend(imm, 21)
}

fun constructStoreImmediate(inst: Instruction): Int {
    val imm_11_5 = inst.getField(InstructionField.IMM_11_5)
    val imm_4_0 = inst.getField(InstructionField.IMM_4_0)
    var imm = 0
    imm = setBitslice(imm, imm_11_5, 5, 12)
    imm = setBitslice(imm, imm_4_0, 0, 5)
    return signExtend(imm, 12)
}