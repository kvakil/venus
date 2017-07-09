package venus.simulator

import venus.riscv.Instruction
import venus.simulator.impl.* // ktlint-disable no-wildcard-imports

/** Describes each instruction for dispatching */
enum class InstructionDispatcher(val implementation: InstructionImplementation,
                                 val tests: List<DispatchTest>) {

    add(ADD.implementation, ADD.tests),
    addi(ADDI.implementation, ADDI.tests),
    and(AND.implementation, AND.tests),
    andi(ANDI.implementation, ANDI.tests),
    auipc(AUIPC.implementation, AUIPC.tests),
    beq(BEQ.implementation, BEQ.tests),
    bge(BGE.implementation, BGE.tests),
    bgeu(BGEU.implementation, BGEU.tests),
    blt(BLT.implementation, BLT.tests),
    bltu(BLTU.implementation, BLTU.tests),
    bne(BNE.implementation, BNE.tests),
    jal(JAL.implementation, JAL.tests),
    jalr(JALR.implementation, JALR.tests),
    lb(LB.implementation, LB.tests),
    lbu(LBU.implementation, LBU.tests),
    lh(LH.implementation, LH.tests),
    lhu(LHU.implementation, LHU.tests),
    lui(LUI.implementation, LUI.tests),
    lw(LW.implementation, LW.tests),
    or(OR.implementation, OR.tests),
    ori(ORI.implementation, ORI.tests),
    sb(SB.implementation, SB.tests),
    sh(SH.implementation, SH.tests),
    slli(SLLI.implementation, SLLI.tests),
    slt(SLT.implementation, SLT.tests),
    slti(SLTI.implementation, SLTI.tests),
    sltiu(SLTIU.implementation, SLTIU.tests),
    sltu(SLTU.implementation, SLTU.tests),
    srai(SRAI.implementation, SRAI.tests),
    srli(SRLI.implementation, SRLI.tests),
    sub(SUB.implementation, SUB.tests),
    sw(SW.implementation, SW.tests),
    xor(XOR.implementation, XOR.tests),
    xori(XORI.implementation, XORI.tests),
    ;
    companion object {
        /** Find the first Implementation which passes all the tests */
        fun dispatch(key: Instruction): InstructionImplementation?
        = InstructionDispatcher.values().firstOrNull {
            dispatch -> dispatch.tests.all({ it(key) })
        }?.implementation
    }
}