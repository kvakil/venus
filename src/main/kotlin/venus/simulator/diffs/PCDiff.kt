package venus.simulator.diffs

import venus.simulator.Diff
import venus.simulator.SimulatorState

class PCDiff(val pc: Int) : Diff {
    override operator fun invoke(state: SimulatorState) { state.pc = pc }
}