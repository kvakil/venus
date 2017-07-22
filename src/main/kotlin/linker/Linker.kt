package venus.linker

import venus.riscv.MemorySegments
import venus.riscv.Program
import venus.assembler.AssemblerError

data class RelocationInfo(val label: String, val offset: Int)

object Linker {
    /* TODO: refactor this into multiple functions */
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
                    throw AssemblerError("label $label used but not defined")

            val relocator = RelocatorDispatcher.dispatch(inst) ?:
                    throw AssemblerError("don't know how to relocate instruction")

            relocator(inst, offset, toAddress)
        }

        return linkedProgram
    }

    /* TODO: add a real .globl assembler directive */
    private fun isGlobalLabel(lbl: String) = !lbl.startsWith("_")
}