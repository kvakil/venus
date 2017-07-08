package venus.simulator

class SimulatorState {
    private val regs = IntArray(32)
    var pc: Int = 0
    fun getReg(i: Int) = regs[i]
    fun setReg(i: Int, v: Int) { if (i != 0) regs[i] = v }
}