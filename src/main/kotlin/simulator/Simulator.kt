package venus.simulator

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.riscv.MemorySegments
import venus.riscv.Program

/** Right now, this is a loose wrapper around SimulatorState
    Eventually, it will support debugging. */
class Simulator(prog: Program) {
    val state = SimulatorState()
    var maxpc = MemorySegments.TEXT_BEGIN
    var cycles = 0

    init {
        state.pc = MemorySegments.TEXT_BEGIN
        for (inst in prog.insts) {
            /* TODO: abstract away instruction length */
            state.mem.storeWord(maxpc, inst.getField(InstructionField.ENTIRE))
            maxpc += inst.length
        }

        var dataOffset = MemorySegments.STATIC_BEGIN
        for (datum in prog.dataSegment) {
            state.mem.storeByte(dataOffset, datum.toInt())
            dataOffset += 1
        }
    }

    fun isDone(): Boolean = state.pc >= maxpc || cycles > 500

    fun run() {
        while (!isDone()) {
            step()
            cycles += 1
        }
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