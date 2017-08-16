package venus.riscv

fun userStringToInt(s: String): Int {
    if (isCharacterLiteral(s)) {
        return characterLiteralToInt(s)
    }
    val radix = when (s.take(2)) {
        "0x" -> 16
        "0b" -> 2
        else -> return s.toLong().toInt()
    }
    return s.drop(2).toLong(radix).toInt()
}

private fun isCharacterLiteral(s: String) =
        s.first() == '\'' && s.last() == '\''

private fun characterLiteralToInt(s: String): Int {
    val stripSingleQuotes = s.drop(1).dropLast(1)
    val jsonString = "\"$stripSingleQuotes\""
    try {
        val parsed = JSON.parse<String>(jsonString)
        if (parsed.isEmpty()) throw NumberFormatException("charater literal $s is empty")
        if (parsed.length > 1) throw NumberFormatException("charater literal $s too long")
        return parsed[0].toInt()
    } catch (e: Throwable) {
        throw NumberFormatException("could not parse character literal $s")
    }
}
