package venus.assembler

import venus.riscv.Instruction

class Program {
    private val insts: MutableList<Instruction>
    private val labels: HashMap<String, Int>
    init {
        insts = ArrayList<Instruction>()
        labels = HashMap<String, Int>()
    }

    fun add(inst: Instruction) = insts.add(inst)

    fun addLabel(lbl: String, offset: Int) = labels.put(lbl, offset)

    fun getLabelLocation(lbl: String): Int? = labels.get(lbl)

    /* TODO: relocation table and linker */
    fun addBranch(lbl: String) {}
    fun addJump(lbl: String) {}

    /* TODO: add dump formats */
    fun dump(): List<Instruction> = insts
}