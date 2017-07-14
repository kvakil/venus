package venus.linker

import venus.riscv.MemorySegments
import venus.riscv.Program
import venus.assembler.AssemblerError

data class RelocationInfo(val label: String, val offset: Int)

object Linker {
    fun link(progs: List<Program>): Program {
        val linkedProgram = Program()
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

            for ((label, offset) in prog.relocationTable) {
                toRelocate.add(RelocationInfo(label, textTotalOffset + offset))
            }

            for (inst in prog.insts) {
                linkedProgram.add(inst)
            }

            linkedProgram.addAllToData(prog.dataSegment)

            textTotalOffset += prog.textSize
            dataTotalOffset += prog.dataSize
        }

        for ((label, offset) in toRelocate) {
            /* FIXME: variable instruction sizes WILL break this */
            val inst = linkedProgram.insts[offset / 4]
            val toAddress = globalTable.get(label)
            if (toAddress == null) {
                throw AssemblerError("label ${label} used but not defined")
            }

            val relocator = RelocatorDispatcher.dispatch(inst)
            if (relocator == null) {
                throw AssemblerError("don't know how to relocate instruction")
            }
            relocator(inst, offset, toAddress)
        }

        return linkedProgram
    }

    /* TODO: add a real .globl assembler directive */
    private fun isGlobalLabel(lbl: String) = !lbl.startsWith("_")
}