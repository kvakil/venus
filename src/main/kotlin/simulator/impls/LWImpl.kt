package venus.simulator.impls

import venus.simulator.impls.types.LoadTypeImpl
import venus.simulator.Simulator

object LWImpl : LoadTypeImpl() {
    override fun evaluate(sim: Simulator, vrs1: Int, imm: Int): Int {
        return sim.loadWord(vrs1 + imm)
    }
}
