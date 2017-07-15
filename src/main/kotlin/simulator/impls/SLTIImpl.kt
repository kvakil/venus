package venus.simulator.impls

import venus.simulator.impls.types.ITypeImpl

object SLTIImpl : ITypeImpl() {
    override fun evaluate(vrs1: Int, imm: Int): Int = if (vrs1 < imm) 1 else 0
}
