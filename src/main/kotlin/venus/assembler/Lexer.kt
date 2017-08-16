package venus.assembler

typealias LineTokens = List<String>

/**
 * A singleton which can be used to lex a given line.
 */
object Lexer {
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
                '\'' -> inCharacter = !escaped && !inString
                '"' -> inString = !escaped && !inCharacter
                ':' -> {
                    if (!inString && !inCharacter) {
                        wasLabel = true
                        if (previousWords.isNotEmpty()) {
                            throw AssemblerError("label $currentWord in the middle of an instruction")
                        }
                    }
                }
                ' ', '\t', '(', ')', ',' -> wasDelimiter = !inString && !inCharacter
            }
            escaped = !escaped && ch == '\\'

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
}
