package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object XORImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int {
        return vrs1 xor vrs2
    }
}
