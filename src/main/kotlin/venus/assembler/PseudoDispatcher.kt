package venus.assembler

import venus.assembler.pseudos.* // ktlint-disable no-wildcard-imports

/** Describes each instruction for writing */
enum class PseudoDispatcher(val pw: PseudoWriter) {
    beqz(BEQZ),
    bgez(BGEZ),
    bgt(BGT),
    bgtu(BGTU),
    bgtz(BGTZ),
    ble(BLE),
    bleu(BLEU),
    blez(BLEZ),
    bltz(BLTZ),
    bnez(BNEZ),
    call(CALL),
    jal(JAL),
    jalr(JALR),
    j(J),
    jr(JR),
    la(LA),
    lb(Load),
    lbu(Load),
    lh(Load),
    lhu(Load),
    li(LI),
    lw(Load),
    mv(MV),
    neg(NEG),
    nop(NOP),
    not(NOT),
    ret(RET),
    sb(Store),
    seqz(SEQZ),
    sgtz(SGTZ),
    sh(Store),
    sltz(SLTZ),
    snez(SNEZ),
    sw(Store),
    tail(TAIL)
    ;
}
