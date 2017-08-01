package venus.riscv.insts.dsl

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

class RTypeInstruction(
        name: String,
        length: Int,
        opcode: Int,
        funct3: Int,
        funct7: Int,
        private val eval32: (Int, Int) -> Int,
        private val eval64: (Long, Long) -> Long = { _, _ -> TODO("no rv64 for $this") }
) : Instruction(name, length, Regex("$SPACES?$REGISTER$DELIMITERS$REGISTER$DELIMITERS$REGISTER$SPACES?")) {
    init {
        ifields.add(FieldEqual(InstructionField.OPCODE, opcode))
        ifields.add(FieldEqual(InstructionField.FUNCT3, funct3))
        ifields.add(FieldEqual(InstructionField.FUNCT7, funct7))
    }

    override fun impl32(mcode: MachineCode, sim: Simulator) {
        val rs1 = mcode[InstructionField.RS1]
        val rs2 = mcode[InstructionField.RS2]
        val rd = mcode[InstructionField.RD]
        val vrs1 = sim.getReg(rs1)
        val vrs2 = sim.getReg(rs2)
        sim.setReg(rd, eval32(vrs1, vrs2))
        sim.incrementPC(mcode.length)
    }

    override fun impl64(mcode: MachineCode, sim: Simulator) { TODO("impl64") }

    override fun write(prog: Program, mcode: MachineCode, args: List<String>) {
        mcode[InstructionField.RD] = regNameToNumber(args[0])
        mcode[InstructionField.RS1] = regNameToNumber(args[1])
        mcode[InstructionField.RS2] = regNameToNumber(args[2])
    }
}
