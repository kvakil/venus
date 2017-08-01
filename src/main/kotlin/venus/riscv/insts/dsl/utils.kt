package venus.riscv.insts.dsl

import venus.riscv.userStringToInt

/**
 * Converts a register name to its ID.
 *
 * Accepts ABI names (e.g., ra, fp) and RISC-V names (e.g., x1, x8)
 *
 * @param reg the name of the register
 * @return the ID of the register
 *
 * @throws IllegalArgumentException if given an invalid register
 */
internal fun regNameToNumber(reg: String): Int {
    if (reg.startsWith("x")) {
        val ret = reg.drop(1).toInt()
        if (ret in 0..31) return ret
        throw IllegalArgumentException("register ${reg} not recognized")
    }
    return when (reg) {
        "zero" -> 0
        "ra" -> 1
        "sp" -> 2
        "gp" -> 3
        "tp" -> 4
        "t0" -> 5
        "t1" -> 6
        "t2" -> 7
        "s0", "fp" -> 8
        "s1" -> 9
        "a0" -> 10
        "a1" -> 11
        "a2" -> 12
        "a3" -> 13
        "a4" -> 14
        "a5" -> 15
        "a6" -> 16
        "a7" -> 17
        "s2" -> 18
        "s3" -> 19
        "s4" -> 20
        "s5" -> 21
        "s6" -> 22
        "s7" -> 23
        "s8" -> 24
        "s9" -> 25
        "s10" -> 26
        "s11" -> 27
        "t3" -> 28
        "t4" -> 29
        "t5" -> 30
        "t6" -> 31
        else -> throw IllegalArgumentException("register ${reg} not recognized")
    }
}

/**
 * Gets the immediate from a string, checking if it is in range.
 *
 * @param str the immediate as a string
 * @param min the minimum allowable value of the immediate
 * @param max the maximum allowable value of the immediate
 * @return the immediate, as an integer
 *
 * @throws IllegalArgumentException if the wrong number of arguments is given
 */
internal fun getImmediate(str: String, min: Int, max: Int): Int {
    val imm = try {
        userStringToInt(str)
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException("invalid number, got ${str} (might be too large?)")
    }

    if (imm !in min..max)
        throw IllegalArgumentException("immediate out of range")

    return imm
}

/**
 * Sign extends v of sz bits to a 32 bit integer
 *
 * @param v the number to sign extend
 * @param sz the number of bits that v takes
 */
internal fun signExtend(v: Int, sz: Int): Int = v shl (32 - sz) shr (32 - sz)

internal fun setBitslice(x: Int, y: Int, start: Int, end: Int): Int {
    val mask: Int = ((1L shl end) - (1L shl start)).inv().toInt()
    return (mask and x) or (y shl start)
}

internal fun compareUnsigned(v1: Int, v2: Int): Int {
    return when {
        v1 == v2 -> 0
        0 <= v1 && v1 < v2 -> -1
        v2 < 0 && v2 < v1 -> -1
        else -> 1
    }
}
