package venus.riscv.insts.dsl

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

private const val MIN_I_VALUE = -2048
private const val MAX_I_VALUE = 2047

class ITypeInstruction(
        name: String,
        length: Int,
        opcode: Int,
        funct3: Int,
        private val eval32: (Int, Int) -> Int,
        private val eval64: (Long, Long) -> Long = { _, _ -> TODO("no rv64 for $this") }
) : Instruction(name, length) {
    init {
        ifields.add(FieldEqual(InstructionField.OPCODE, opcode))
        ifields.add(FieldEqual(InstructionField.FUNCT3, funct3))
    }

    override fun impl32(mcode: MachineCode, sim: Simulator) {
        val rs1: Int = mcode[InstructionField.RS1]
        val imm: Int = signExtend(mcode[InstructionField.IMM_11_0], 12)
        val rd: Int = mcode[InstructionField.RD]
        val vrs1: Int = sim.getReg(rs1)
        sim.setReg(rd, eval32(vrs1, imm))
        sim.incrementPC(mcode.length)
    }

    override fun impl64(mcode: MachineCode, sim: Simulator) { TODO("impl64") }

    override fun write(prog: Program, mcode: MachineCode, args: List<String>) {
        mcode[InstructionField.RD] = regNameToNumber(args[0])
        mcode[InstructionField.RS1] = regNameToNumber(args[1])
        mcode[InstructionField.IMM_11_0] = getImmediate(args[2], MIN_I_VALUE, MAX_I_VALUE)
    }
}
