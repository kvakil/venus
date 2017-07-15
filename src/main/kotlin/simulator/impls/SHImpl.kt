package venus.simulator.impls

import venus.simulator.impls.types.STypeImpl
import venus.simulator.Memory

object SHImpl : STypeImpl() {
    override fun evaluate(mem: Memory, addr: Int, vrs2: Int) {
        mem.storeHalfWord(addr, vrs2)
    }
}
