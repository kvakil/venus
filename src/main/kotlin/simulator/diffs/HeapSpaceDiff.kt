package venus.simulator.diffs

import venus.simulator.Diff
import venus.simulator.SimulatorState

class HeapSpaceDiff(val heapEnd: Int) : Diff {
    override operator fun invoke(state: SimulatorState) { state.heapEnd = heapEnd }
}