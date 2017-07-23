package venus.simulator.impls

import venus.simulator.impls.types.LoadTypeImpl
import venus.simulator.Simulator

object LHImpl : LoadTypeImpl() {
    override fun evaluate(sim: Simulator, vrs1: Int, imm: Int): Int {
        return signExtend(sim.loadHalfWord(vrs1 + imm), 16)
    }
}
