package venus.simulator.diffs

import venus.simulator.Diff
import venus.simulator.SimulatorState

class MemoryDiff(val addr: Int, val value: Int) : Diff {
    override operator fun invoke(state: SimulatorState) {
        state.mem.storeWord(addr, value)
    }
}