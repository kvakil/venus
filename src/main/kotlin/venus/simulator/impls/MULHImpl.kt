package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object MULHImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int = ((vrs1.toLong() * vrs2.toLong()) ushr 32).toInt()
}
