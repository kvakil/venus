package venus.assembler

/**
 * A singleton which can be used to lex a given line.
 *
 * @todo replace with a better lexer (ANTLR?)
 */
object Lexer {
    /** A list of characters which should be treated as spaces. */
    private val DELIMITERS = listOf('(', ')', ',')

    /**
     * Replaces all comments (anything after a #) from a line.
     *
     * @param line the line to remove comments from
     * @return the line after being stripped, possibly the original
     */
    private fun stripComment(line: String): String {
        val commentTextRemoved = line.replaceAfter('#', "")
        return with (commentTextRemoved) { if (last() == '#') dropLast(1) else this }
    }

    /**
     * Removes all comments and extra whitespace in preparation for lexing the label.
     *
     * @param line the line to clean
     */
    private fun cleanForLabel(line: String) = stripComment(line).trimStart()

    /**
     * Gets the label from the line, or returns "" if no label is present.
     *
     * @param line the line to get the label from
     * @return the label on the line, if any
     */
    private fun getLabel(line: String) = cleanForLabel(line).substringBefore(':', "")

    /**
     * Returns the original line, excluding any starting label.
     *
     * @param line the line to strip the label off
     * @return the original line, with any starting label removed
     */
    private fun stripLabel(line: String): String {
        val labelRemoved = line.replaceAfter('#', "")
        return with (labelRemoved) { if (first() == ':') drop(1) else this }
    }

    /**
     * Returns the line in preparation for splitting into tokens.
     *
     * This consists of several steps:
     *      - Removing any comments and labels.
     *      - Replacing any delimiters by spaces.
     *      - Replace any consecutive whitespace with a single space.
     *
     * @param line the line to clean
     * @return a cleaned line (as described above)
     */
    private fun cleanLine(line: String): String {
        var cleanedLine = line
        cleanedLine = stripComment(cleanedLine)
        cleanedLine = stripLabel(cleanedLine)
        for (delimiter in DELIMITERS) {
            cleanedLine = cleanedLine.replace(delimiter, ' ')
        }
        cleanedLine = cleanedLine.trim()
        cleanedLine = cleanedLine.replace(Regex("\\s+"), " ")
        return cleanedLine
    }

    /**
     * Lex a line into a label (if there) and a list of arguments.
     *
     * @param line the line to lex
     * @return a pair containing the label and tokens
     */
    fun lexLine(line: String) = Pair(getLabel(line), cleanLine(line).split(' '))

    /** A regex pattern describing a string. */
    private const val STRING = """((?:[^"\\]|\\.)*)"""

    /**
     * A pattern describing an .asciiz directive.
     */
    private val asciizPattern = """\s*\.asciiz\s+$STRING\s*""".toRegex()

    /**
     * Lex an asciiz directive.
     *
     * @param line the line (which should probably contain .asciiz)
     * @return the string inside quotes
     */
    fun lexAsciizDirective(line: String): String? = asciizPattern.matchEntire(stripLabel(line))?.groups?.get(1)?.value
}