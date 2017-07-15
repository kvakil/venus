package venus.simulator.impls

import venus.simulator.impls.types.ShiftTypeImpl

object SLLIImpl : ShiftTypeImpl() {
    override fun evaluate(vrs1: Int, shamt: Int): Int = vrs1 shl shamt
}
