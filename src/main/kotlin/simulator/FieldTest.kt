package venus.simulator

import venus.riscv.Instruction
import venus.riscv.InstructionField

class FieldTest(val ifield: InstructionField, val required: Int): DispatchTest {
    override fun matches(inst: Instruction): Boolean = inst.getField(ifield) == required
}