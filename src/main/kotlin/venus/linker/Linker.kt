package venus.linker

import venus.riscv.MemorySegments
import venus.riscv.Program
import venus.assembler.AssemblerError

/** Contains the byte offset which must be relocated and the label it should point to */
data class RelocationInfo(val label: String, val offset: Int)

/**
 * A singleton which links a list of programs into one program.
 *
 * @see LinkedProgram
 */
object Linker {
    /**
     * Links together the given list of programs.
     *
     * This consists of several steps:
     *      - Collect all the labels in each program.
     *      - Keep track of all the instructions which need to be relocated.
     *      - Append all the instructions together.
     *      - Append all of the debugging info together.
     *      - Append all of the data segments together
     * Once the above is done, all the appropriate relocation is performed.
     *
     * @param progs a list of programs which will be linked
     * @return a [LinkedProgram] which can be used in the [venus.simulator.Simulator]
     * @todo refactor this into multiple functions
     */
    fun link(progs: List<Program>): LinkedProgram {
        val linkedProgram = LinkedProgram()
        val globalTable = HashMap<String, Int>()
        val toRelocate = ArrayList<RelocationInfo>()
        var textTotalOffset = 0
        var dataTotalOffset = 0

        for (prog in progs) {
            for ((label, offset) in prog.labels) {
                val start = if (offset >= MemorySegments.STATIC_BEGIN) {
                    dataTotalOffset
                } else {
                    textTotalOffset
                }

                if (isGlobalLabel(label)) {
                    globalTable.put(label, start + offset)
                }
            }

            prog.relocationTable.forEach {
                (label, offset) -> toRelocate.add(RelocationInfo(label, textTotalOffset + offset))
            }
            prog.insts.forEach { linkedProgram.prog.add(it) }
            prog.debugInfo.forEach {
                linkedProgram.dbg.add(ProgramDebugInfo(prog.name, it))
            }
            prog.dataSegment.forEach { linkedProgram.prog.addToData(it) }
            textTotalOffset += prog.textSize
            dataTotalOffset += prog.dataSize
        }

        for ((label, offset) in toRelocate) {
            /* FIXME: variable instruction sizes WILL break this */
            val inst = linkedProgram.prog.insts[offset / 4]

            val toAddress = globalTable.get(label) ?:
                    throw AssemblerError("label $label used but not defined globally")

            val relocator = RelocatorDispatcher.dispatch(inst) ?:
                    throw AssemblerError("don't know how to relocate instruction")

            relocator(inst, offset, toAddress)
        }

        return linkedProgram
    }

    /**
     * @param label the name of a label
     * @return true if the given label is global
     * @todo add a real .globl assembler directive
     */
    private fun isGlobalLabel(label: String) = !label.startsWith("_")
}