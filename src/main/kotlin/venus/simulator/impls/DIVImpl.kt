package venus.simulator.impls

import venus.simulator.impls.types.RTypeImpl

object DIVImpl : RTypeImpl() {
    override fun evaluate(vrs1: Int, vrs2: Int): Int {
        if (vrs2 == 0) {
            return -1
        }

        if (vrs1 == Int.MIN_VALUE && vrs2 == -1) {
            return vrs1
        }

        return vrs1 / vrs2
    }
}
