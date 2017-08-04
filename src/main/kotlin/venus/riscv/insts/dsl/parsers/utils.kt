package venus.riscv.insts.dsl.parsers

import venus.assembler.AssemblerError

internal fun checkArgsLength(argsSize: Int, required: Int) {
    if (argsSize != required)
        throw AssemblerError("got $argsSize arguments but expected $required")
}

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
internal fun regNameToNumber(reg: String): Int {
    if (reg.startsWith("x")) {
        val ret = reg.drop(1).toInt()
        if (ret in 0..31) return ret
        throw AssemblerError("register $reg not recognized")
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
        else -> throw AssemblerError("register $reg not recognized")
    }
}
