package venus.assembler

import venus.riscv.Instruction

class Program {
    private val insts: MutableList<Instruction>
    private val labels: HashMap<String, Int>
    private var size: Int
    init {
        insts = ArrayList<Instruction>()
        labels = HashMap<String, Int>()
        size = 0
    }

    fun add(inst: Instruction) {
        insts.add(inst)
        size += inst.length
    }

    fun addLabel(lbl: String, offset: Int) = labels.put(lbl, offset)

    fun getLabelOffset(lbl: String): Int? {
        val loc = labels.get(lbl)
        if (loc == null) return null
        return loc - size
    }

    /* TODO: relocation table and linker */
    fun addJump(lbl: String) {}

    /* TODO: add dump formats */
    fun dump(): List<Instruction> = insts
}