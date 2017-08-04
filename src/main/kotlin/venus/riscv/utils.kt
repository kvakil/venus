package venus.riscv

fun userStringToInt(s: String): Int {
    if (isCharacterLiteral(s)) {
        return s[1].toInt()
    }
    val radix = when (s.take(2)) {
        "0x" -> 16
        "0b" -> 2
        else -> return s.toLong().toInt()
    }
    return s.drop(2).toLong(radix).toInt()
}

private fun isCharacterLiteral(s: String) =
        s.length == 3 && s[0] == '\'' && s[2] == '\''
