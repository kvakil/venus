package venus.simulator.impls

import venus.simulator.impls.types.ITypeImpl

object SLTIUImpl : ITypeImpl() {
    override fun evaluate(vrs1: Int, imm: Int): Int = if (compareUnsigned(vrs1, imm) < 0) 1 else 0
}
