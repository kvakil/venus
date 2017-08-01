package venus.linker

import venus.linker.relocators.ADDIRelocator
import venus.linker.relocators.AUIPCRelocator
import venus.linker.relocators.JALRelocator
import venus.linker.relocators.LoadRelocator
import venus.riscv.InstructionFormat
import venus.riscv.MachineCode
import venus.riscv.formats.*

/** Describes each instruction which may be relocated */
enum class RelocatorDispatcher(val relocator: Relocator, val iform: InstructionFormat) {
    ADDI(ADDIRelocator, ADDIForm),
    AUIPC(AUIPCRelocator, AUIPCForm),
    JAL(JALRelocator, JALForm),
    LB(LoadRelocator, LBForm),
    LBU(LoadRelocator, LBUForm),
    LH(LoadRelocator, LHForm),
    LHU(LoadRelocator, LHUForm),
    LW(LoadRelocator, LWForm),
    ;
    companion object {
        /** Find the first Relocator which passes all the tests */
        fun dispatch(inst: MachineCode): Relocator? =
        RelocatorDispatcher.values().firstOrNull {
            dispatch -> dispatch.iform.ifields.all {
                (ifield, required) -> inst.getField(ifield) == required
            }
        }?.relocator
    }
}