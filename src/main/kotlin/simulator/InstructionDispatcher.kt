package venus.simulator

import venus.riscv.Instruction
import venus.simulator.impl.*

/** Describes each instruction for dispatching */
enum class InstructionDispatcher(val implementation: InstructionImplementation,
                                 val tests: List<DispatchTest>) {

    add(ADD.implementation, ADD.tests)
    ;
    companion object {
        /** Find the first Implementation which passes all the tests */
        fun dispatch(key: Instruction): InstructionDispatcher?
        = InstructionDispatcher.values().firstOrNull {
            dispatch -> dispatch.tests.all({ it.matches(key) })
        }
    }
}