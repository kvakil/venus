package venus.linker

import venus.assembler.AssemblerError
import venus.riscv.MemorySegments
import venus.riscv.Program
import venus.riscv.insts.dsl.Instruction

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

                if (prog.isGlobalLabel(label)) {
                    val previousValue = globalTable.put(label, start + offset)
                    if (previousValue != null) {
                        throw AssemblerError("label $label defined global in two different files")
                    }
                    if (label == "main") {
                        linkedProgram.startPC = start + offset
                    }
                }
            }

            prog.insts.forEach(linkedProgram.prog::add)
            prog.debugInfo.forEach {
                linkedProgram.dbg.add(ProgramDebugInfo(prog.name, it))
            }
            prog.dataSegment.forEach(linkedProgram.prog::addToData)

            for ((label, offset) in prog.relocationTable) {
                /* try to determine each relocation locally */
                val toAddress = prog.labels.get(label)
                if (toAddress != null) {
                    relocateInstruction(linkedProgram, toAddress, textTotalOffset + offset)
                } else {
                    toRelocate.add(RelocationInfo(label, textTotalOffset + offset))
                }
            }

            textTotalOffset += prog.textSize
            dataTotalOffset += prog.dataSize
        }

        for ((label, offset) in toRelocate) {
            val toAddress = globalTable.get(label) ?:
                    throw AssemblerError("label $label used but not defined")

            relocateInstruction(linkedProgram, toAddress, offset)
            /* FIXME: variable instruction sizes WILL break this */
        }

        return linkedProgram
    }

    fun relocateInstruction(linkedProgram: LinkedProgram, toAddress: Int, offset: Int) {
        val mcode = linkedProgram.prog.insts[offset / 4]
        Instruction[mcode].relocate(mcode, offset, toAddress)
    }

}