package venus.assembler

object Lexer {
    private fun stripComment(line: String) = line.replaceAfter('#', "").replace("#", "")
    private fun getLabel(line: String) = stripComment(line).substringBefore(':', "")
    private fun stripLabel(line: String) = line.replaceBefore(':', "").replace(":", "")
    private fun cleanLine(line: String): String {
        var cleanedLine = line
        cleanedLine = stripComment(cleanedLine)
        cleanedLine = stripLabel(cleanedLine)
        cleanedLine = cleanedLine.replace(',', ' ')
        cleanedLine = cleanedLine.replace('(', ' ')
        cleanedLine = cleanedLine.replace(')', ' ')
        cleanedLine = cleanedLine.trim()
        cleanedLine = cleanedLine.replace(Regex("\\s+"), " ")
        return cleanedLine
    }

    /* TODO: better lexer (ANTLR?) */

    /** Lex a line into a label (if there) and a list of arguments */
    fun lexLine(line: String) = Pair(getLabel(line), cleanLine(line).split(' '))
}