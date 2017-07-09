package venus.riscv

data class FieldEqual(val ifield: InstructionField, val required: Int)
data class InstructionFormat(val ifields: List<FieldEqual>)