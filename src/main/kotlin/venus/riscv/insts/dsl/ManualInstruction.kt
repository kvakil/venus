package venus.riscv.insts.dsl

import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

class ManualInstruction (
        name: String,
        length: Int,
        fieldPredicates: List<FieldEqual>,
        private val eval32: (MachineCode, Simulator) -> Unit,
        private val eval64: (MachineCode, Simulator) -> Unit = { _, _ -> TODO("no rv64 for $this") },
        private val filler: (Program, MachineCode, List<String>) -> Unit
) : Instruction(name, length) {
    init {
        ifields.addAll(fieldPredicates)
    }

    override fun impl32(mcode: MachineCode, sim: Simulator) { eval32(mcode, sim) }

    override fun impl64(mcode: MachineCode, sim: Simulator) { eval64(mcode, sim) }

    override fun fill(prog: Program, mcode: MachineCode, args: List<String>) { filler(prog, mcode, args) }
}
