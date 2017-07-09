package venus.simulator

import venus.riscv.Instruction

/** Right now, this is a loose wrapper around SimulatorState
    Eventually, it will support debugging. */
class Simulator(insts: List<Instruction>) {
    val state = SimulatorState()

    init {
        var pc = 0
        for (inst in insts) {
            state.mem.storeWord(pc, inst.encoding)
            pc += 4
        }
    }
}