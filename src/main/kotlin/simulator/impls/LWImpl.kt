package venus.simulator.impls

import venus.simulator.impls.types.LoadTypeImpl
import venus.simulator.Memory

object LWImpl : LoadTypeImpl() {
    override fun evaluate(mem: Memory, vrs1: Int, imm: Int): Int {
        return mem.loadWord(vrs1 + imm)
    }
}
