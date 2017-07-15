package venus.simulator.impls

import venus.simulator.impls.types.STypeImpl
import venus.simulator.Simulator

object SHImpl : STypeImpl() {
    override fun evaluate(sim: Simulator, addr: Int, vrs2: Int) {
        sim.storeHalfWord(addr, vrs2)
    }
}
