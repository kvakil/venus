package venus.riscv

import venus.assembler.DebugInfo
import venus.linker.RelocationInfo

class Program {
    /* TODO: abstract away these variables */
    public val name: String
    public val insts: MutableList<Instruction>
    public val debugInfo: MutableList<DebugInfo>
    public val labels: HashMap<String, Int>
    public val relocationTable: MutableList<RelocationInfo>
    public val dataSegment: MutableList<Byte>
    public var textSize: Int
    public var dataSize: Int
    constructor(programName: String = "anonymous") {
        name = programName
        insts = ArrayList<Instruction>()
        debugInfo = ArrayList<DebugInfo>()
        labels = HashMap<String, Int>()
        relocationTable = ArrayList<RelocationInfo>()
        dataSegment = ArrayList<Byte>()
        textSize = 0
        dataSize = 0
    }

    fun add(inst: Instruction) {
        insts.add(inst)
        textSize += inst.length
    }

    fun addToData(byte: Byte) {
        dataSegment.add(byte)
        dataSize++
    }

    fun addAllToData(bytes: List<Byte>) {
        dataSegment.addAll(bytes)
        dataSize += bytes.size
    }

    fun addDebugInfo(dbg: DebugInfo) {
        while (debugInfo.size < insts.size) {
            debugInfo.add(dbg)
        }
    }

    fun addLabel(label: String, offset: Int) = labels.put(label, offset)

    fun getLabelOffset(label: String): Int? {
        val loc = labels.get(label)
        if (loc == null) return null
        return loc - textSize
    }

    /* TODO: relocation table and linker */
    fun addRelocation(label: String, offset: Int = textSize) =
        relocationTable.add(RelocationInfo(label, offset))

    /* TODO: add dump formats */
    fun dump(): List<Instruction> = insts
}