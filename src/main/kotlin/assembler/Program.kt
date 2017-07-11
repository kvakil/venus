package venus.assembler

import venus.riscv.Instruction

class Program {
    /* TODO: abstract away these variables */
    public val insts: MutableList<Instruction>
    public val labels: HashMap<String, Int>
    public val relocationTable: MutableList<RelocationInfo>
    public var size: Int
    init {
        insts = ArrayList<Instruction>()
        labels = HashMap<String, Int>()
        relocationTable = ArrayList<RelocationInfo>()
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
    fun addJump(lbl: String) = relocationTable.add(RelocationInfo(lbl, size))
 
    /* TODO: add dump formats */
    fun dump(): List<Instruction> = insts
}