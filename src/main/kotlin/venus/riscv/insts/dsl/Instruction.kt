package venus.riscv.insts.dsl

import venus.riscv.InstructionField
import venus.riscv.MachineCode
import venus.riscv.Program
import venus.simulator.Simulator

data class FieldEqual(val ifield: InstructionField, val required: Int)

abstract class Instruction(
        private val name: String,
        private val length: Int,
        private val lex: Regex,
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

    fun write(prog: Program, args: String) {
        val mcode = MachineCode(0)
        for ((ifield, required) in ifields) {
            mcode[ifield] = required
        }
        val matchResult = lex.matchEntire(args) ?:
                throw IllegalArgumentException("could not lex $name $args")
        write(prog, mcode, matchResult.groupValues.drop(1))
        prog.add(mcode)
    }

    abstract fun write(prog: Program, mcode: MachineCode, args: List<String>)

    private fun matches(mcode: MachineCode): Boolean = ifields.all {
        (ifield, required) -> mcode[ifield] == required
    }

    override fun toString() = name
}

internal const val SPACES = """(?:\s+)"""
internal const val SEPARATORS = ""","""
internal const val DELIMITERS = """(?:$SPACES|$SEPARATORS)+"""

internal const val REGISTER_BY_NUMBER =
        "x(?:31|30|29|28|27|26|25|24|23|22|21|20|19|18|17|16|15|14|13|12|11|10|9|8|7|6|5|4|3|2|1|0)"
internal const val REGISTER_BY_NAME =
        "(?:zero|ra|sp|gp|tp|fp|s10|s11|t[0-6]|s[0-9]|a[0-7])"
internal const val REGISTER = """($REGISTER_BY_NUMBER|$REGISTER_BY_NAME)"""

internal const val BIN_IMMEDIATE = """(?:0[bB][01]+)"""
internal const val HEX_IMMEDIATE = """(?:0[xX][0-9a-fA-F]+)"""
internal const val DEC_IMMEDIATE = """(?:\d+)"""
internal const val IMMEDIATE = """($BIN_IMMEDIATE|$HEX_IMMEDIATE|$DEC_IMMEDIATE)"""
