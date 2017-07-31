package venus.assembler.writers

import venus.riscv.userStringToInt
import venus.assembler.AssemblerError

/**
 * Converts a register name to its ID.
 *
 * Accepts ABI names (e.g., ra, fp) and RISC-V names (e.g., x1, x8)
 *
 * @param reg the name of the register
 * @return the ID of the register
 *
 * @throws AssemblerError if given an invalid register
 */
fun regNameToNumber(reg: String): Int {
    if (reg.startsWith("x")) {
        val ret = reg.drop(1).toInt()
        if (ret in 0..31) return ret
        throw AssemblerError("register ${reg} not recognized")
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
        else -> throw AssemblerError("register ${reg} not recognized")
    }
}

/**
 * Checks to make sure the right number of arguments are given to an instruction
 *
 * @param args the arguments given to an instruction
 * @param required the expected number of arguments
 *
 * @throws AssemblerError if the wrong number of arguments were given
 */
fun checkArgsLength(args: List<String>, required: Int) {
    if (args.size != required)
        throw AssemblerError("got ${args.size} arguments, wanted ${required}")
}

/**
 * Gets the immediate from a string, checking if it is in range.
 *
 * @param str the immediate as a string
 * @param min the minimum allowable value of the immediate
 * @param max the maximum allowable value of the immediate
 * @return the immediate, as an integer
 *
 * @throws AssemblerError if the wrong number of arguments is given
 */
fun getImmediate(str: String, min: Int, max: Int): Int {
    val imm = try {
        userStringToInt(str)
    } catch (e: NumberFormatException) {
        throw AssemblerError("invalid number, got ${str} (might be too large?)")
    }

    if (imm !in min..max)
        throw AssemblerError("immediate out of range")

    return imm
}