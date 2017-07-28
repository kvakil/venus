package venus.linker.relocators

import venus.riscv.Instruction
import venus.riscv.InstructionField
import venus.linker.Relocator

object LoadRelocator : Relocator() {
    override operator fun invoke(inst: Instruction, pc: Int, target: Int) {
        val imm = target - (pc - 4)
        var imm_lo = imm and 0b111111111111
        inst.setField(InstructionField.IMM_11_0, imm_lo)
    }
}
