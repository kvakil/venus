package venus.assembler

import venus.riscv.Instruction

class Program {
    /* TODO: abstract away these variables */
    public val insts: MutableList<Instruction>
    public val labels: HashMap<String, Int>
    public val relocationTable: MutableList<RelocationInfo>
    public val dataSegment: MutableList<Int>
    public var textSize: Int
    public var dataSize: Int
    init {
        insts = ArrayList<Instruction>()
        labels = HashMap<String, Int>()
        relocationTable = ArrayList<RelocationInfo>()
        dataSegment = ArrayList<Int>()
        textSize = 0
        dataSize = 0
    }

    fun add(inst: Instruction) {
        insts.add(inst)
        textSize += inst.length
    }

    fun addToData(words: List<Int>) {
        dataSegment.addAll(words)
        dataSize += 4 * words.size
    }

    fun addLabel(lbl: String, offset: Int) = labels.put(lbl, offset)

    fun getLabelOffset(lbl: String): Int? {
        val loc = labels.get(lbl)
        if (loc == null) return null
        return loc - textSize
    }

    /* TODO: relocation table and linker */
    fun addJump(lbl: String) = relocationTable.add(RelocationInfo(lbl, textSize))

    /* TODO: add dump formats */
    fun dump(): List<Instruction> = insts
}