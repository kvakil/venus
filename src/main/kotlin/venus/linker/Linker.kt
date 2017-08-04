package venus.linker

import venus.assembler.AssemblerError
import venus.riscv.MemorySegments
import venus.riscv.Program
import venus.riscv.insts.dsl.relocators.Relocator

/**
 * Describes how to relocate a given instructions
 *
 * @param relocator the relocator to use
 * @param label the target label
 * @param offset the byte offset the instruction is at
 */
data class RelocationInfo(val relocator: Relocator, val offset: Int, val label: String)

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
                val location = start + offset

                if (prog.isGlobalLabel(label)) {
                    val previousValue = globalTable.put(label, location)
                    if (previousValue != null) {
                        throw AssemblerError("label $label defined global in two different files")
                    }
                    if (label == "main") {
                        linkedProgram.startPC = location
                    }
                }
            }

            prog.insts.forEach(linkedProgram.prog::add)
            prog.debugInfo.forEach {
                linkedProgram.dbg.add(ProgramDebugInfo(prog.name, it))
            }
            prog.dataSegment.forEach(linkedProgram.prog::addToData)

            for ((relocator, offset, label) in prog.relocationTable) {
                val toAddress = prog.labels.get(label)
                val location = textTotalOffset + offset
                if (toAddress != null) {
                    /* TODO: fix this for variable length instructions */
                    val mcode = linkedProgram.prog.insts[location / 4]
                    println("1pre: $mcode @ $offset @ $toAddress $label")
                    relocator(mcode, location, toAddress)
                    println("1psre: $mcode @ $offset @ $toAddress $label")
                } else {
                    /* need to relocate globally */
                    toRelocate.add(RelocationInfo(relocator, location, label))
                }
            }

            textTotalOffset += prog.textSize
            dataTotalOffset += prog.dataSize
        }

        for ((relocator, offset, label) in toRelocate) {
            val toAddress = globalTable.get(label) ?:
                    throw AssemblerError("label $label used but not defined")

            val mcode = linkedProgram.prog.insts[offset / 4]
            println("2pre: $mcode @ $offset @ $toAddress $label")
            relocator(mcode, offset, toAddress)
            println("2psre: $mcode @ $offset @ $toAddress $label")
        }

        return linkedProgram
    }
}
