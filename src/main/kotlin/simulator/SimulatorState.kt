package venus.simulator

import venus.riscv.MemorySegments

class SimulatorState {
    private val regs = IntArray(32)
    val mem = Memory()
    var pc: Int = 0
    var heapEnd: Int = MemorySegments.HEAP_BEGIN
    fun getReg(i: Int) = regs[i]
    fun setReg(i: Int, v: Int) { if (i != 0) regs[i] = v }
}