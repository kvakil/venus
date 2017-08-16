package venus.assembler

typealias LineTokens = List<String>

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
        return with (commentTextRemoved) { if (isNotEmpty() && last() == '#') dropLast(1) else this }
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
        val labelRemoved = line.replaceBefore(':', "")
        return with (labelRemoved) { if (isNotEmpty() && first() == ':') drop(1) else this }
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

    //fun lexLine(line: String) = Pair(getLabel(line), cleanLine(line).split(' '))

    private fun addNonemptyWord(previous: ArrayList<String>, next: StringBuilder) {
        val word = next.toString()
        if (word.isNotEmpty()) {
            previous += word
        }
    }

    /**
     * Lex a line into a label (if there) and a list of arguments.
     *
     * @param line the line to lex
     * @return a pair containing the label and tokens
     * @see LineTokens
     */
    fun lexLine(line: String): Pair<LineTokens, LineTokens> {
        var currentWord = StringBuilder("")
        val previousWords = ArrayList<String>()
        val labels = ArrayList<String>()
        var escaped = false
        var inCharacter = false
        var inString = false
        var foundComment = false

        for (ch in line) {
            var wasDelimiter = false
            var wasLabel = false
            when (ch) {
                '#' -> foundComment = !inString && !inCharacter
                '\'' -> inString = !escaped && !inString && !inCharacter
                '"' -> inCharacter = !escaped && !inString && !inCharacter
                '\\' -> escaped = !escaped
                ':' -> {
                    if (!inString && !inCharacter) {
                        wasLabel = true
                        if (previousWords.isNotEmpty()) {
                            throw AssemblerError("label in the middle of a string")
                        }
                    }
                }
                ' ', '\t', '(', ')', ',' -> wasDelimiter = !inString && !inCharacter
            }

            if (foundComment) break

            if (wasDelimiter) {
                addNonemptyWord(previousWords, currentWord)
                currentWord = StringBuilder("")
            } else if (wasLabel) {
                addNonemptyWord(labels, currentWord)
                currentWord = StringBuilder("")
            } else {
                currentWord.append(ch)
            }
        }

        addNonemptyWord(previousWords, currentWord)

        return Pair(labels, previousWords)
    }

    /**
     * A pattern describing an .asciiz directive.
     */
    private val asciizPattern = """\s*\.asciiz\s+("(?:[^"\\]|\\.)*")\s*""".toRegex()

    /**
     * Lex an asciiz directive.
     *
     * @param line the line (which should probably contain .asciiz)
     * @return the string inside quotes
     */
    fun lexAsciizDirective(line: String): String? {
        val rawString = asciizPattern.matchEntire(stripLabel(line))?.groups?.get(1)?.value
        return if (rawString != null) JSON.parse(rawString) else null
    }
}
