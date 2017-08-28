package venus.simulator

sealed class Diff {
    abstract operator fun invoke(state: SimulatorState)
}

class HeapSpaceDiff(val heapEnd: Int) : Diff() {
    override operator fun invoke(state: SimulatorState) { state.heapEnd = heapEnd }
}

class MemoryDiff(val addr: Int, val value: Int) : Diff() {
    override operator fun invoke(state: SimulatorState) { state.mem.storeWord(addr, value) }
}

class PCDiff(val pc: Int) : Diff() {
    override operator fun invoke(state: SimulatorState) { state.pc = pc }
}

class RegisterDiff(val id: Int, val v: Int) : Diff() {
    override operator fun invoke(state: SimulatorState) = state.setReg(id, v)
}
