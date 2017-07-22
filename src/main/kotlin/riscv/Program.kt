package venus.riscv

import venus.assembler.DebugInfo
import venus.linker.RelocationInfo

class Program(public val name: String = "anonymous") {
    /* TODO: abstract away these variables */
    public val insts = ArrayList<Instruction>()
    public val debugInfo = ArrayList<DebugInfo>()
    public val labels = HashMap<String, Int>()
    public val relocationTable = ArrayList<RelocationInfo>()
    public val dataSegment = ArrayList<Byte>()
    public var textSize = 0
    public var dataSize = 0

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
        return loc?.minus(textSize)
    }

    /* TODO: relocation table and linker */
    fun addRelocation(label: String, offset: Int = textSize) =
        relocationTable.add(RelocationInfo(label, offset))

    /* TODO: add dump formats */
    fun dump(): List<Instruction> = insts
}