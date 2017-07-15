package venus.simulator.diffs

import venus.simulator.Diff
import venus.simulator.SimulatorState

class RegisterDiff(val id: Int, val v: Int) : Diff {
    override operator fun invoke(state: SimulatorState) = state.setReg(id, v)
}