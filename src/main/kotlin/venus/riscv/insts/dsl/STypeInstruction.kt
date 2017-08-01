package venus.riscv.insts.dsl

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

class STypeInstruction(
        name: String,
        length: Int,
        opcode: Int,
        funct3: Int,
        private val store32: (Simulator, Int, Int) -> Unit,
        private val store64: (Simulator, Long, Long) -> Unit = { _, _, _ -> TODO("no rv64 for $this") }
) : Instruction(name, length) {
    init {
        ifields.add(FieldEqual(InstructionField.OPCODE, opcode))
        ifields.add(FieldEqual(InstructionField.FUNCT3, funct3))
    }

    override fun impl32(mcode: MachineCode, sim: Simulator) {
        val rs1 = mcode[InstructionField.RS1]
        val rs2 = mcode[InstructionField.RS2]
        val imm = constructStoreImmediate(mcode)
        val addr = sim.getReg(rs1) + imm
        val vrs2 = sim.getReg(rs2)
        store32(sim, addr, vrs2)
        sim.incrementPC(mcode.length)
    }

    override fun impl64(mcode: MachineCode, sim: Simulator) { TODO("impl64") }

    override fun fill(prog: Program, mcode: MachineCode, args: List<String>) {
        mcode[InstructionField.RD] = regNameToNumber(args[0])
        prog.addRelocation(args[1])
    }
}

private fun constructStoreImmediate(mcode: MachineCode): Int {
    val imm_11_5 = mcode[InstructionField.IMM_11_5]
    val imm_4_0 = mcode[InstructionField.IMM_4_0]
    var imm = 0
    imm = setBitslice(imm, imm_11_5, 5, 12)
    imm = setBitslice(imm, imm_4_0, 0, 5)
    return signExtend(imm, 12)
}
