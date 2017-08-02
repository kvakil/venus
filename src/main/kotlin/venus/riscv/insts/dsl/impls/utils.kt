package venus.riscv.insts.dsl.impls

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
