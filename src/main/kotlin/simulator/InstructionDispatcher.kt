package venus.simulator

import venus.riscv.Instruction
import venus.riscv.InstructionFormat
import venus.simulator.impls.* // ktlint-disable no-wildcard-imports
import venus.riscv.formats.* // ktlint-disable no-wildcard-imports

/** Describes each instruction for dispatching */
enum class InstructionDispatcher(val implementation: InstructionImplementation,
                                 val iform: InstructionFormat) {

    add(ADDImpl, ADDForm),
    addi(ADDIImpl, ADDIForm),
    and(ANDImpl, ANDForm),
    andi(ANDIImpl, ANDIForm),
    auipc(AUIPCImpl, AUIPCForm),
    beq(BEQImpl, BEQForm),
    bge(BGEImpl, BGEForm),
    bgeu(BGEUImpl, BGEUForm),
    blt(BLTImpl, BLTForm),
    bltu(BLTUImpl, BLTUForm),
    bne(BNEImpl, BNEForm),
    ecall(ECALLImpl, ECALLForm),
    jal(JALImpl, JALForm),
    jalr(JALRImpl, JALRForm),
    lb(LBImpl, LBForm),
    lbu(LBUImpl, LBUForm),
    lh(LHImpl, LHForm),
    lhu(LHUImpl, LHUForm),
    lui(LUIImpl, LUIForm),
    lw(LWImpl, LWForm),
    or(ORImpl, ORForm),
    ori(ORIImpl, ORIForm),
    sb(SBImpl, SBForm),
    sh(SHImpl, SHForm),
    slli(SLLIImpl, SLLIForm),
    slt(SLTImpl, SLTForm),
    slti(SLTIImpl, SLTIForm),
    sltiu(SLTIUImpl, SLTIUForm),
    sltu(SLTUImpl, SLTUForm),
    srai(SRAIImpl, SRAIForm),
    srli(SRLIImpl, SRLIForm),
    sub(SUBImpl, SUBForm),
    sw(SWImpl, SWForm),
    xor(XORImpl, XORForm),
    xori(XORIImpl, XORIForm),
    ;
    companion object {
        /** Find the first Implementation which passes all the tests */
        fun dispatch(inst: Instruction): InstructionImplementation? =
        InstructionDispatcher.values().firstOrNull {
            dispatch -> dispatch.iform.ifields.all {
                (ifield, required) -> inst.getField(ifield) == required
            }
        }?.implementation
    }
}