package venus.simulator

import venus.riscv.Instruction
import venus.riscv.InstructionField

class FieldTest(val ifield: InstructionField, val required: Int): DispatchTest {
    override operator fun invoke(inst: Instruction): Boolean = inst.getField(ifield) == required
}