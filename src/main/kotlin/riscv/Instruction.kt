package venus.riscv

fun numberOfTrailingZeros(n: Int): Int {
    var mask: Int = 1
    for (i in 0 until 32) {
        if (n and mask != 0) return i
        mask = mask shl 1
    }
    return 32
}

class Instruction(private var encoding: Int) {
    val length = 4

    fun getField(ifield: InstructionField): Int {
        return (encoding and ifield.mask) ushr numberOfTrailingZeros(ifield.mask)
    }

    fun setField(ifield: InstructionField, value: Int) {
        encoding = encoding and ifield.mask.inv()
        encoding = encoding or ((value shl numberOfTrailingZeros(ifield.mask)) and ifield.mask)
    }
}