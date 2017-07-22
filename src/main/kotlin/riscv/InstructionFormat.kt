package venus.riscv

/**
 * A class asserting that an instruction field equals a certain value.
 *
 * @param ifield the instruction field
 * @param required its required value
 */
data class FieldEqual(val ifield: InstructionField, val required: Int)

/**
 * A class which says what format an instruction must take.
 *
 * e.g., "opcode must be 0b011011" AND "funct3 must be 0b110"...
 *
 * @see FieldEqual
 *
 * @param ifields a list of fields and their required values
 */
data class InstructionFormat(val ifields: List<FieldEqual>)
