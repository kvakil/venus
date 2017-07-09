package venus.simulator

import venus.riscv.Instruction
import venus.riscv.InstructionField

/** Right now, this is a loose wrapper around SimulatorState
    Eventually, it will support debugging. */
class Simulator(insts: List<Instruction>) {
    val state = SimulatorState()
    var maxpc = 0

    init {
        for (inst in insts) {
            /* TODO: abstract away instruction length */
            state.mem.storeWord(maxpc, inst.getField(InstructionField.ENTIRE))
            maxpc += inst.length
        }
    }

    fun isDone(): Boolean = state.pc >= maxpc

    fun run() {
        while (!isDone()) step()
    }

    fun step(): Boolean {
        /* TODO: abstract away instruction length */
        val inst: Instruction = Instruction(state.mem.loadWord(state.pc))
        val impl = InstructionDispatcher.dispatch(inst)
        if (impl == null) return false
        impl(inst, state)
        return true
    }
}