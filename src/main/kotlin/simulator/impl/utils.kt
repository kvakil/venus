package venus.simulator.impl

/** Sign extends v of sz bits to a 32 bit integer */
fun signExtend(v: Int, sz: Int): Int = v shl (32 - sz) shr (32 - sz)

/** Returns x[end:start] = y */
fun setBitslice(x: Int, y: Int, start: Int, end: Int): Int {
    val mask: Int = ((1 shl end) - (1 shl start)).inv()
    return (mask and x) or (y shl start)
}