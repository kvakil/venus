package venus.riscv

import venus.riscv.InstructionFormat

fun numberOfTrailingZeros(n: Int): Int {
    var mask: Int = 1;
    for (i in 0 until 32) {
        if (n and mask != 0) return i
        mask = mask shl 1
    }
    return 32
}

class Instruction(protected val encoding: Int) {
    fun getField(iform: InstructionFormat): Int {
        return (encoding and iform.mask) ushr numberOfTrailingZeros(iform.mask)
    }
}