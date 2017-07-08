package venus.simulator.impl

/** Sign extends v of sz bits to a 32 bit integer */
fun signExtend(v: Int, sz: Int): Int = v shl (32 - sz) shr (32 - sz)