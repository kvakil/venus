package venus.riscv

/**
 * Represents the machine code of an instruction.
 *
 * For now this only supports RV32.
 *
 * @param encoding the underlying machine code of the instruction
 */
class MachineCode(private var encoding: Int) {
    val length = 2

    /**
     * Returns the value of the given instruction field for this instruction.
     *
     * @param ifield the instruction field to get
     * @return the value of the given instruction field
     */
    operator fun get(ifield: InstructionField): Int {
        val mask = ((1L shl ifield.hi) - (1L shl ifield.lo)).toInt()
        return (encoding and mask) ushr ifield.lo
    }

    /**
     * Sets an instruction field to the given value.
     *
     * @param ifield the instruction field to set
     * @param value the value to set the field to
     */
    operator fun set(ifield: InstructionField, value: Int) {
        val mask = ((1L shl ifield.hi) - (1L shl ifield.lo)).toInt()
        encoding = encoding and mask.inv()
        encoding = encoding or ((value shl ifield.lo) and mask)
    }

    override fun toString() = encoding.toString()
}