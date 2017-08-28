package venus.simulator

import venus.linker.LinkedProgram
import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.MemorySegments
import venus.riscv.insts.dsl.Instruction

/** Right now, this is a loose wrapper around SimulatorState
    Eventually, it will support debugging. */
class Simulator(val linkedProgram: LinkedProgram) {
    private val state = SimulatorState()
    private var maxpc = MemorySegments.TEXT_BEGIN
    private var cycles = 0
    private val history = History()
    private val preInstruction = ArrayList<Diff>()
    private val postInstruction = ArrayList<Diff>()
    private val breakpoints: Array<Boolean>

    init {
        for (inst in linkedProgram.prog.insts) {
            /* TODO: abstract away instruction length */
            state.mem.storeWord(maxpc, inst[InstructionField.ENTIRE])
            maxpc += inst.length
        }

        var dataOffset = MemorySegments.STATIC_BEGIN
        for (datum in linkedProgram.prog.dataSegment) {
            state.mem.storeByte(dataOffset, datum.toInt())
            dataOffset++
        }

        state.pc = linkedProgram.startPC ?: MemorySegments.TEXT_BEGIN
        state.setReg(2, MemorySegments.STACK_BEGIN)
        state.setReg(3, MemorySegments.STATIC_BEGIN)

        breakpoints = Array<Boolean>(linkedProgram.prog.insts.size, { false })
    }

    fun isDone(): Boolean = getPC() >= maxpc

    fun run() {
        while (!isDone()) {
            step()
            cycles++
        }
    }

    fun step(): List<Diff> {
        preInstruction.clear()
        postInstruction.clear()
        /* TODO: abstract away instruction length */
        val mcode: MachineCode = getNextInstruction()
        Instruction[mcode].impl32(mcode, this)
        history.add(preInstruction)
        return postInstruction.toList()
    }

    fun undo(): List<Diff> {
        if (!canUndo()) return emptyList() /* TODO: error here? */
        val diffs = history.pop()
        for (diff in diffs) {
            diff(state)
        }
        return diffs
    }

    fun canUndo() = !history.isEmpty()

    fun getReg(id: Int) = state.getReg(id)

    fun setReg(id: Int, v: Int) {
        preInstruction.add(RegisterDiff(id, state.getReg(id)))
        state.setReg(id, v)
        postInstruction.add(RegisterDiff(id, state.getReg(id)))
    }

    fun setRegNoUndo(id: Int, v: Int) {
        state.setReg(id, v)
    }

    fun toggleBreakpointAt(idx: Int): Boolean {
        breakpoints[idx] = !breakpoints[idx]
        return breakpoints[idx]
    }

    fun atBreakpoint() = breakpoints[state.pc / 4]

    fun getPC() = state.pc

    fun setPC(newPC: Int) {
        preInstruction.add(PCDiff(state.pc))
        state.pc = newPC
        postInstruction.add(PCDiff(state.pc))
    }

    fun incrementPC(inc: Int) {
        preInstruction.add(PCDiff(state.pc))
        state.pc += inc
        postInstruction.add(PCDiff(state.pc))
    }

    fun loadByte(addr: Int): Int = state.mem.loadByte(addr)
    fun loadHalfWord(addr: Int): Int = state.mem.loadHalfWord(addr)
    fun loadWord(addr: Int): Int = state.mem.loadWord(addr)

    fun storeByte(addr: Int, value: Int) {
        preInstruction.add(MemoryDiff(addr, loadWord(addr)))
        state.mem.storeByte(addr, value)
        postInstruction.add(MemoryDiff(addr, loadWord(addr)))
    }

    fun storeHalfWord(addr: Int, value: Int) {
        preInstruction.add(MemoryDiff(addr, loadWord(addr)))
        state.mem.storeHalfWord(addr, value)
        postInstruction.add(MemoryDiff(addr, loadWord(addr)))
    }

    fun storeWord(addr: Int, value: Int) {
        preInstruction.add(MemoryDiff(addr, loadWord(addr)))
        state.mem.storeWord(addr, value)
        postInstruction.add(MemoryDiff(addr, loadWord(addr)))
    }

    fun getHeapEnd() = state.heapEnd

    fun addHeapSpace(bytes: Int) {
        preInstruction.add(HeapSpaceDiff(state.heapEnd))
        state.heapEnd += bytes
        postInstruction.add(HeapSpaceDiff(state.heapEnd))
    }

    private fun getInstructionLength(short0: Int): Int {
        if ((short0 and 0b11) != 0b11) {
            return 2
        } else if ((short0 and 0b11111) != 0b11111) {
            return 4
        } else if ((short0 and 0b111111) == 0b011111) {
            return 6
        } else if ((short0 and 0b1111111) == 0b111111) {
            return 8
        } else {
            throw SimulatorError("instruction lengths > 8 not supported")
        }
    }

    private fun getNextInstruction(): MachineCode {
        val short0 = loadHalfWord(getPC())
        val length = getInstructionLength(short0)
        if (length != 4) {
            throw SimulatorError("instruction length != 4 not supported")
        }

        val short1 = loadHalfWord(getPC() + 2)

        return MachineCode((short1 shl 16) or short0)
    }
}
