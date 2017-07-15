package venus.simulator.impls

import venus.simulator.impls.types.BTypeImpl

object BLTUImpl : BTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Boolean = (compareUnsigned(vrs1, vrs2) < 0)
}
