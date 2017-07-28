package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object MULHSUImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int {
        val leftSigned: Long = vrs1.toLong()
        val rightUnsigned: Long = (vrs2.toLong() shl 32) ushr 32
        return ((leftSigned * rightUnsigned) ushr 32).toInt()
    }
}
