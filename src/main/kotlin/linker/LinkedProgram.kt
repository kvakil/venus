package venus.linker

import venus.assembler.DebugInfo
import venus.riscv.Program

data class ProgramDebugInfo(val programName: String, val dbg: DebugInfo)

class LinkedProgram {
    val prog = Program()
    val dbg = ArrayList<ProgramDebugInfo>()
}