package venus.riscv

import venus.riscv.InstructionFormat

abstract class Instruction(protected val encoding: Int) {
    val opcode: Int get() = encoding and InstructionFormat.OPCODE.mask
}