package venus.simulator

import venus.riscv.Instruction
import venus.simulator.impl.*

/** Describes each instruction for dispatching */
enum class InstructionDispatcher(val implementation: InstructionImplementation,
                                 val tests: List<DispatchTest>) {

    add(ADD.implementation, ADD.tests),
    addi(ADDI.implementation, ADDI.tests),
    and(AND.implementation, AND.tests),
    or(OR.implementation, OR.tests),
    slli(SLLI.implementation, SLLI.tests),
    slt(SLT.implementation, SLT.tests),
    sltu(SLTU.implementation, SLTU.tests),
    sub(SUB.implementation, SUB.tests),
    xor(XOR.implementation, XOR.tests),
    ;
    companion object {
        /** Find the first Implementation which passes all the tests */
        fun dispatch(key: Instruction): InstructionImplementation?
        = InstructionDispatcher.values().firstOrNull {
            dispatch -> dispatch.tests.all({ it(key) })
        }?.implementation
    }
}