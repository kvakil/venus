package venus.simulator

import venus.riscv.Instruction

/** Right now, this is a loose wrapper around SimulatorState
    Eventually, it will support debugging. */
class Simulator(insts: List<Instruction>) {
    val state = SimulatorState()
    var maxpc = 0

    init {
        for (inst in insts) {
            state.mem.storeWord(maxpc, inst.encoding)
            maxpc += 4
        }
    }
}