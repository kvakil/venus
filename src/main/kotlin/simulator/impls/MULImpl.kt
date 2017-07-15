package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object MULImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int = vrs1 * vrs2
}