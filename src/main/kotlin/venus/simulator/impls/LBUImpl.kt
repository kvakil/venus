package venus.simulator.impls

import venus.simulator.impls.types.LoadTypeImpl
import venus.simulator.Simulator

object LBUImpl : LoadTypeImpl() {
    override fun evaluate(sim: Simulator, vrs1: Int, imm: Int): Int {
        return sim.loadByte(vrs1 + imm)
    }
}
