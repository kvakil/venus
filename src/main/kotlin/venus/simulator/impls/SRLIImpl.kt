package venus.simulator.impls

import venus.simulator.impls.types.ShiftTypeImpl

object SRLIImpl : ShiftTypeImpl() {
    override fun evaluate(vrs1: Int, shamt: Int): Int = vrs1 ushr shamt
}
