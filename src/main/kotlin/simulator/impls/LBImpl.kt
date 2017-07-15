package venus.simulator.impls

import venus.simulator.impls.types.LoadTypeImpl
import venus.simulator.Memory

object LBImpl : LoadTypeImpl() {
    override fun evaluate(mem: Memory, vrs1: Int, imm: Int): Int {
        return signExtend(mem.loadByte(vrs1 + imm), 8)
    }
}
