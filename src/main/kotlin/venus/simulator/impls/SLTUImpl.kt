package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object SLTUImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int = if (compareUnsigned(vrs1, vrs2) < 0) 1 else 0
}
