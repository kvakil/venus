package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object SLTImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int = if (vrs1 < vrs2) 1 else 0
}
