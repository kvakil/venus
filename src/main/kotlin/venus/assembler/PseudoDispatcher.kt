package venus.assembler

import venus.assembler.pseudos.* // ktlint-disable no-wildcard-imports

/** Describes each instruction for writing */
enum class PseudoDispatcher(val pw: PseudoWriter) {
    j(J),
    jal(JAL),
    jalr(JALR),
    jr(JR),
    la(LA),
    lb(LoadPseudo),
    lbu(LoadPseudo),
    lh(LoadPseudo),
    lhu(LoadPseudo),
    li(LI),
    lw(LoadPseudo),
    mv(MV),
    nop(NOP),
    ret(RET),
    ;
}