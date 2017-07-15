package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object REMImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int {
        return vrs1 % vrs2
    }
}
