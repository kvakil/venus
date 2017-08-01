package venus.riscv.insts.dsl

import venus.assembler.AssemblerError
import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

private const val MIN_B_VALUE = -2048
private const val MAX_B_VALUE = 2047

class BTypeInstruction(
        name: String,
        length: Int,
        opcode: Int,
        funct3: Int,
        private val cond32: (Int, Int) -> Boolean,
        private val cond64: (Long, Long) -> Boolean = { _, _ -> TODO("no rv64 for $this") }
) : Instruction(name, length) {
    init {
        ifields.add(FieldEqual(InstructionField.OPCODE, opcode))
        ifields.add(FieldEqual(InstructionField.FUNCT3, funct3))
    }

    override fun impl32(mcode: MachineCode, sim: Simulator) {
        val rs1: Int = mcode[InstructionField.RS1]
        val rs2: Int = mcode[InstructionField.RS2]
        val imm: Int = constructBranchImmediate(mcode)
        val vrs1: Int = sim.getReg(rs1)
        val vrs2: Int = sim.getReg(rs2)
        if (cond32(vrs1, vrs2))
            sim.incrementPC(imm)
        else
            sim.incrementPC(mcode.length)
    }

    override fun impl64(mcode: MachineCode, sim: Simulator) { TODO("impl64") }

    override fun write(prog: Program, mcode: MachineCode, args: List<String>) {
        mcode[InstructionField.RS1] = regNameToNumber(args[0])
        mcode[InstructionField.RS2] = regNameToNumber(args[1])

        val label = args[2]

        val imm = prog.getLabelOffset(label) ?:
                throw AssemblerError("could not find label $label")
        if (imm !in MIN_B_VALUE..MAX_B_VALUE)
            throw AssemblerError("branch to $label too far")

        mcode[InstructionField.IMM_11_B] = imm shr 11
        mcode[InstructionField.IMM_4_1] = imm shr 1
        mcode[InstructionField.IMM_12] = imm shr 12
        mcode[InstructionField.IMM_10_5] = imm shr 5
    }
}

private fun constructBranchImmediate(mcode: MachineCode): Int {
    val imm_11 = mcode[InstructionField.IMM_11_B]
    val imm_4_1 = mcode[InstructionField.IMM_4_1]
    val imm_10_5 = mcode[InstructionField.IMM_10_5]
    val imm_12 = mcode[InstructionField.IMM_12]
    var imm = 0
    imm = setBitslice(imm, imm_11, 11, 12)
    imm = setBitslice(imm, imm_4_1, 1, 5)
    imm = setBitslice(imm, imm_10_5, 5, 11)
    imm = setBitslice(imm, imm_12, 12, 13)
    return signExtend(imm, 13)
}
