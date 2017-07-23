package venus.simulator.impls

import venus.simulator.impls.types.STypeImpl
import venus.simulator.Simulator

object SBImpl : STypeImpl() {
    override fun evaluate(sim: Simulator, addr: Int, vrs2: Int) {
        sim.storeByte(addr, vrs2)
    }
}
