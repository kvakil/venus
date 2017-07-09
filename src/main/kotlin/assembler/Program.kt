package venus.assembler

import venus.riscv.Instruction

class Program {
    private val insts: MutableList<Instruction>
    init {
        insts = ArrayList<Instruction>()
    }

    fun add(inst: Instruction) = insts.add(inst)

    /* TODO: fill this in [use insts.size relative to label] */
    fun getLabelOffset(lbl: String): Int? = 1

    /* TODO: add dump formats */
    fun dump(): List<Instruction> = insts
}