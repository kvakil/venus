package venus.riscv

/** A singleton containing constants which say where various segments start */
object MemorySegments {
    /** Memory address where the stack segment starts (growing downwards) */
    const val STACK_BEGIN = 0x7fff_fffc
    /** Memory address where the heap segment starts */
    const val HEAP_BEGIN = 0x1000_8000
    /** Memory address where the data segment starts */
    const val STATIC_BEGIN = 0x1000_0000
    /**
     * Memory address where the text segment starts
     * @fixme a bug currently prevents TEXT_BEGIN from being anything other than 0
     */
    const val TEXT_BEGIN = 0x0000_0000
}