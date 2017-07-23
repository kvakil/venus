package venus.linker

import venus.riscv.Instruction
import venus.riscv.InstructionFormat
import venus.riscv.formats.* // ktlint-disable no-wildcard-imports
import venus.linker.relocators.* // ktlint-disable no-wildcard-imports

/** Describes each instruction which may be relocated */
enum class RelocatorDispatcher(val relocator: Relocator, val iform: InstructionFormat) {
    ADDI(ADDIRelocator, ADDIForm),
    AUIPC(AUIPCRelocator, AUIPCForm),
    JAL(JALRelocator, JALForm)
    ;
    companion object {
        /** Find the first Relocator which passes all the tests */
        fun dispatch(inst: Instruction): Relocator? =
        RelocatorDispatcher.values().firstOrNull {
            dispatch -> dispatch.iform.ifields.all {
                (ifield, required) -> inst.getField(ifield) == required
            }
        }?.relocator
    }
}