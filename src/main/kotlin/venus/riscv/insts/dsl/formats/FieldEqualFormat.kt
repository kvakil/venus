package venus.riscv.insts.dsl.formats

import venus.riscv.InstructionField
import venus.riscv.MachineCode

data class FieldEqual(val ifield: InstructionField, val required: Int)

class FieldEqualFormat(private val ifields: List<FieldEqual>) : InstructionFormat {
    override fun matches(mcode: MachineCode): Boolean = ifields.all {
        (ifield, required) -> mcode[ifield] == required
    }

    override fun fill(): MachineCode {
        val mcode = MachineCode(0)
        for ((ifield, required) in ifields) {
            mcode[ifield] = required
        }
        return mcode
    }
}
