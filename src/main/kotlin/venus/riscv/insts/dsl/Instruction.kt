package venus.riscv.insts.dsl

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

data class FieldEqual(val ifield: InstructionField, val required: Int)

abstract class Instruction(
        private val name: String,
        private val length: Int,
        val relocate: (MachineCode, Int, Int) -> Unit = { _, _, _ -> TODO("no relocate for $this") }
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

    fun write(prog: Program, args: List<String>) {
        val mcode = MachineCode(0)
        for ((ifield, required) in ifields) {
            mcode[ifield] = required
        }
        write(prog, mcode, args)
        prog.add(mcode)
    }

    abstract fun write(prog: Program, mcode: MachineCode, args: List<String>)

    private fun matches(mcode: MachineCode): Boolean = ifields.all {
        (ifield, required) -> mcode[ifield] == required
    }

    override fun toString() = name
}
