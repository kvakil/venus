package venus.riscv

fun userStringToInt(s: String): Int {
    val radix = when (s.take(2)) {
        "0x" -> 16
        "0b" -> 2
        else -> return s.toLong().toInt()
    }
    return s.drop(2).toLong(radix).toInt()
}