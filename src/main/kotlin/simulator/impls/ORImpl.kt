package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object ORImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int {
        return vrs1 or vrs2
    }
}
