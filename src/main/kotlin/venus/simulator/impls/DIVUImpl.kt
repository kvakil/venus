package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object DIVUImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int {
        val leftUnsigned: Long = (vrs1.toLong() shl 32) ushr 32
        val rightUnsigned: Long = (vrs2.toLong() shl 32) ushr 32

        if (vrs2 == 0) {
            return vrs1
        }

        return (leftUnsigned / rightUnsigned).toInt()
    }
}
