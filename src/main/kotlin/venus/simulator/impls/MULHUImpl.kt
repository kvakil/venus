package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object MULHUImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int {
        val left: Long = (vrs1.toLong() shl 32) ushr 32
        val right: Long = (vrs2.toLong() shl 32) ushr 32
        return ((left * right) ushr 32).toInt()
    }
}
