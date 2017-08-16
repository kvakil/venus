package venus.riscv.insts.dsl

import venus.assembler.AssemblerError
import venus.riscv.userStringToInt

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
        val hint = when {
            str.length > 4 -> " (might be too large)"
            else -> ""
        }
        throw AssemblerError("invalid number, got $str$hint")
    }

    if (imm !in min..max)
        throw AssemblerError("immediate $str (= $imm) out of range (should be between $min and $max)")

    return imm
}

internal fun compareUnsigned(v1: Int, v2: Int): Int =
        compareUnsignedLong(v1.toLong(), v2.toLong())

internal fun compareUnsignedLong(v1: Long, v2: Long): Int = when {
    v1 == v2 -> 0
    0 <= v1 && v1 < v2 -> -1
    v2 < 0 && v2 < v1 -> -1
    else -> 1
}
