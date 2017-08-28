package venus.simulator

/**
 * Describes how to get _to_ a state from the current state.
 */
sealed class Diff {
    /**
     * Applies a Diff to the given state.
     *
     * @param state the state to apply this diff to
     */
    abstract operator fun invoke(state: SimulatorState)
}

/**
 * Describes a change in the heap state (controlled by `sbrk`).
 *
 * @param heapEnd the address to set the end of the heap to.
 */
class HeapSpaceDiff(val heapEnd: Int) : Diff() {
    override operator fun invoke(state: SimulatorState) { state.heapEnd = heapEnd }
}

/**
 * Describes a change in the memory.
 *
 * Note that for simplicity reason, this has word-level granularity. This will likely be changed once RV64 support is
 * added.
 *
 * @param addr the address which was changed
 * @param value the old word stored at the address.
 */
class MemoryDiff(val addr: Int, val value: Int) : Diff() {
    override operator fun invoke(state: SimulatorState) { state.mem.storeWord(addr, value) }
}

/**
 * Describes a change in the PC.
 *
 * @param pc the address to set the PC to
 */
class PCDiff(val pc: Int) : Diff() {
    override operator fun invoke(state: SimulatorState) { state.pc = pc }
}

/**
 * Describes a change in a register.
 *
 * @param id the ID of the register to change.
 * @param v the value to set the register to.
 */
class RegisterDiff(val id: Int, val v: Int) : Diff() {
    override operator fun invoke(state: SimulatorState) = state.setReg(id, v)
}
