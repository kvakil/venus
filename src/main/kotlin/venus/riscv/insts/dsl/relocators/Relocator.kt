package venus.riscv.insts.dsl.relocators

import venus.riscv.MachineCode

interface Relocator32 {
    operator fun invoke(mcode: MachineCode, pc: Int, target: Int)
}

interface Relocator64 {
    operator fun invoke(mcode: MachineCode, pc: Long, target: Long)
}

class Relocator(private val relocator32: Relocator32, private val relocator64: Relocator64) {
    operator fun invoke(mcode: MachineCode, pc: Number, target: Number, is64: Boolean = false) {
        if (is64) relocator64(mcode, pc.toLong(), target.toLong())
        else relocator32(mcode, pc.toInt(), target.toInt())
    }
}
