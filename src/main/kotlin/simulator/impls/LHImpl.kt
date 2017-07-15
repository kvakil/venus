package venus.simulator.impls

import venus.simulator.impls.types.LoadTypeImpl
import venus.simulator.Memory

object LHImpl : LoadTypeImpl() {
    override fun evaluate(mem: Memory, vrs1: Int, imm: Int): Int {
        return signExtend(mem.loadHalfWord(vrs1 + imm), 16)
    }
}
