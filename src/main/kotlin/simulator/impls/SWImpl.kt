package venus.simulator.impls

import venus.simulator.impls.types.STypeImpl
import venus.simulator.Memory

object SWImpl : STypeImpl() {
    override fun evaluate(mem: Memory, addr: Int, vrs2: Int) {
        mem.storeWord(addr, vrs2)
    }
}
