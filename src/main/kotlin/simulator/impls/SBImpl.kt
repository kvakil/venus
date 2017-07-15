package venus.simulator.impls

import venus.simulator.impls.types.STypeImpl
import venus.simulator.Memory

object SBImpl : STypeImpl() {
    override fun evaluate(mem: Memory, addr: Int, vrs2: Int) {
        mem.storeByte(addr, vrs2)
    }
}
