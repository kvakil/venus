package venus.assembler

object Lexer {
    private fun stripComment(line: String) = line.replaceAfter('#', "").replace("#", "")
    private fun cleanForLabel(line: String) = stripComment(line).trim()
    private fun getLabel(line: String) = cleanForLabel(line).substringBefore(':', "")
    private fun stripLabel(line: String) = line.replaceBefore(':', "").replace(":", "")
    private fun cleanLine(line: String): String {
        var cleanedLine = line
        cleanedLine = stripComment(cleanedLine)
        cleanedLine = stripLabel(cleanedLine)
                        .replace(',', ' ')
                        .replace('(', ' ')
                        .replace(')', ' ')
                        .trim()
                        .replace(Regex("\\s+"), " ")
        return cleanedLine
    }

    /* TODO: better lexer (ANTLR?) */

    /** Lex a line into a label (if there) and a list of arguments */
    fun lexLine(line: String) = Pair(getLabel(line), cleanLine(line).split(' '))

    private val asciizPattern = "\\s*\\.asciiz\\s+\"((?:[^\"\\\\]|\\\\.)*)\"\\s*".toRegex()
    fun lexAsciizDirective(line: String): String? =
        asciizPattern.matchEntire(stripLabel(line))?.groups?.get(1)?.value
}