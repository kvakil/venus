package venus.riscv.insts.dsl

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

private const val MIN_U_VALUE = 0
private const val MAX_U_VALUE = 1048575

class UTypeInstruction(
        name: String,
        length: Int,
        opcode: Int,
        private val eval32: (MachineCode, Simulator) -> Unit,
        private val eval64: (MachineCode, Simulator) -> Unit = { _, _ -> TODO("no rv64 for $this") }
) : Instruction(name, length) {
    init {
        ifields.add(FieldEqual(InstructionField.OPCODE, opcode))
    }

    override fun impl32(mcode: MachineCode, sim: Simulator) { eval32(mcode, sim) }
    override fun impl64(mcode: MachineCode, sim: Simulator) { eval64(mcode, sim) }

    override fun write(prog: Program, mcode: MachineCode, args: List<String>) {
        mcode[InstructionField.RD] = regNameToNumber(args[0])
        mcode[InstructionField.IMM_31_12] = getImmediate(args[1], MIN_U_VALUE, MAX_U_VALUE)
    }
}
