package venus.riscv.insts.dsl

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

class JTypeInstruction(
        name: String,
        length: Int,
        opcode: Int,
        private val eval32: (MachineCode, Simulator) -> Unit,
        private val eval64: (MachineCode, Simulator) -> Unit
) : Instruction(name, length) {
    init {
        ifields.add(FieldEqual(InstructionField.OPCODE, opcode))
    }

    override fun impl32(mcode: MachineCode, sim: Simulator) { eval32(mcode, sim) }

    override fun impl64(mcode: MachineCode, sim: Simulator) { TODO("impl64") }

    override fun write(prog: Program, mcode: MachineCode, args: List<String>) {
        mcode[InstructionField.RD] = regNameToNumber(args[0])
        prog.addRelocation(args[1])
    }
}
