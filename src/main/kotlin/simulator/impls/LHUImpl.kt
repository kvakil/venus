package venus.simulator.impls

import venus.simulator.impls.types.LoadTypeImpl
import venus.simulator.Memory

object LHUImpl : LoadTypeImpl() {
    override fun evaluate(mem: Memory, vrs1: Int, imm: Int): Int {
        return mem.loadHalfWord(vrs1 + imm)
    }
}
