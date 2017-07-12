package venus.riscv

object MemorySegments {
    const val STACK_BEGIN = 0x7fff_fffc
    const val HEAP_BEGIN = 0x1000_8000
    const val STATIC_BEGIN = 0x1000_0000
    const val TEXT_BEGIN = 0x0000_0000
}