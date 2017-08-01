package venus.riscv.insts.dsl

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

class ShiftInstruction(
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
        val rs1 = mcode[InstructionField.RS1]
        val rd = mcode[InstructionField.RD]
        val vrs1 = sim.getReg(rs1)
        val shamt = mcode[InstructionField.SHAMT]
        sim.setReg(rd, eval32(vrs1, shamt))
        sim.incrementPC(mcode.length)
    }

    override fun impl64(mcode: MachineCode, sim: Simulator) { TODO("impl64") }

    /* @todo support srai */
    override fun fill(prog: Program, mcode: MachineCode, args: List<String>) {
        mcode[InstructionField.RD] = regNameToNumber(args[0])
        mcode[InstructionField.RS1] = regNameToNumber(args[1])
        /// mcode[InstructionField.IMM_11_0] = getImmediate(args[2], )
    }
}
