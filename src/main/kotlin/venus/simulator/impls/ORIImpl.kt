package venus.simulator.impls

import venus.simulator.impls.types.ITypeImpl

object ORIImpl : ITypeImpl() {
    override fun evaluate(vrs1: Int, imm: Int): Int = vrs1 or imm
}
