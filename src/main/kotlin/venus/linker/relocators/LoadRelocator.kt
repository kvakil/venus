package venus.linker.relocators

import venus.linker.Relocator
import venus.riscv.InstructionField
import venus.riscv.MachineCode

object LoadRelocator : Relocator() {
    override operator fun invoke(inst: MachineCode, pc: Int, target: Int) {
        val imm = target - (pc - 4)
        val imm_lo = imm and 0b111111111111
        inst.setField(InstructionField.IMM_11_0, imm_lo)
    }
}
