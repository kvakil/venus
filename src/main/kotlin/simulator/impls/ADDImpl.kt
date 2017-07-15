package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object ADDImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int {
        return vrs1 + vrs2
    }
}
