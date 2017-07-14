package venus.assembler

import venus.assembler.pseudos.* // ktlint-disable no-wildcard-imports

/** Describes each instruction for writing */
enum class PseudoDispatcher(val pw: PseudoWriter) {
    j(J),
    jal(JAL),
    la(LA),
    li(LI),
    mv(MV),
    nop(NOP),
    ret(RET),
    ;
}