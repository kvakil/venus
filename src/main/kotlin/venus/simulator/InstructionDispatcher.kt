package venus.simulator

import venus.riscv.InstructionFormat
import venus.riscv.MachineCode
import venus.riscv.formats.*
import venus.simulator.impls.*

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
    div(DIVImpl, DIVForm),
    divu(DIVUImpl, DIVUForm),
    ecall(ECALLImpl, ECALLForm),
    jal(JALImpl, JALForm),
    jalr(JALRImpl, JALRForm),
    mul(MULImpl, MULForm),
    mulh(MULHImpl, MULHForm),
    mulhsu(MULHSUImpl, MULHSUForm),
    mulhu(MULHUImpl, MULHUForm),
    lb(LBImpl, LBForm),
    lbu(LBUImpl, LBUForm),
    lh(LHImpl, LHForm),
    lhu(LHUImpl, LHUForm),
    lui(LUIImpl, LUIForm),
    lw(LWImpl, LWForm),
    or(ORImpl, ORForm),
    ori(ORIImpl, ORIForm),
    rem(REMImpl, REMForm),
    remu(REMUImpl, REMUForm),
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
        fun dispatch(inst: MachineCode): InstructionImplementation? =
        InstructionDispatcher.values().firstOrNull {
            dispatch -> dispatch.iform.ifields.all {
                (ifield, required) -> inst.getField(ifield) == required
            }
        }?.implementation
    }
}