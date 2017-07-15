package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object ANDImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int {
        return vrs1 and vrs2
    }
}
