package venus.assembler

import venus.assembler.pseudos.* // ktlint-disable no-wildcard-imports

/** Describes each instruction for writing */
enum class PseudoDispatcher(val pw: PseudoWriter) {
    j(J),
    jal(JAL),
    jalr(JALR),
    jr(JR),
    la(LA),
    lb(Load),
    lbu(Load),
    lh(Load),
    lhu(Load),
    li(LI),
    lw(Load),
    mv(MV),
    nop(NOP),
    ret(RET),
    ;
}