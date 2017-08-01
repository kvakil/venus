package venus.riscv

import venus.assembler.writers.regNameToNumber
import venus.simulator.Simulator

abstract class Instruction(
        private val name: String,
        private val length: Int,
        private val lex: Regex
) {
    protected val ifields = arrayListOf<FieldEqual>()
    companion object {
        private val allInstructions = arrayListOf<Instruction>()

        operator fun get(mcode: MachineCode): Instruction =
                allInstructions.firstOrNull { it.matches(mcode) }
                        ?: throw IllegalArgumentException("instruction not found for $mcode")

        operator fun get(name: String) =
                allInstructions.firstOrNull { it.name == name }
                        ?: throw IllegalArgumentException("instruction with name $name not found")
    }

    init {
        allInstructions.add(this)
    }

    fun impl(mcode: MachineCode, sim: Simulator) = impl32(mcode, sim)
    abstract fun impl32(mcode: MachineCode, sim: Simulator)
    abstract fun impl64(mcode: MachineCode, sim: Simulator)

    fun write(prog: Program, mcode: MachineCode, args: String) {
        val matchResult = lex.matchEntire(args) ?:
                throw IllegalArgumentException("could not lex $name $args")
        write(prog, mcode, matchResult.groupValues.drop(1))
    }

    abstract fun write(prog: Program, mcode: MachineCode, args: List<String>)

    private fun matches(mcode: MachineCode): Boolean = length == mcode.length && ifields.all {
        (ifield, required) -> mcode[ifield] == required
    }

    override fun toString() = name
}

private const val SPACE = " "
private const val rTypePattern = "(.*)$SPACE(.*)$SPACE(.*)"
class RTypeInstruction(
        name: String,
        length: Int,
        opcode: Int,
        funct3: Int,
        funct7: Int,
        private val eval32: (Int, Int) -> Int,
        private val eval64: (Long, Long) -> Long
) : Instruction(name, length, Regex(rTypePattern)) {
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

private val add = RTypeInstruction(
        name = "add",
        length = 4,
        opcode = 0b0110011,
        funct3 = 0b000,
        funct7 = 0b0000000,
        eval32 = { a, b -> a + b },
        eval64 = { a, b -> a + b }
)
