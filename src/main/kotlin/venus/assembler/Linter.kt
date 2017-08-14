package venus.assembler

@JsName("LintError") data class LintError(val lineNumber: Int, val message: String)

/**
 * Linter for RISC-V code
 *
 * At the moment this implemented by just running the assembler outright, unless the text is too long.
 * A more intelligent approach would run the linter on each line incrementally.
 */
@JsName("Linter") object Linter {
    /**
     * Lints the given text, which is expected to be an entire assembly file
     *
     * @param text the text to lint
     * @fixme this relies on Kotlin using JS array for Array, but it will probably remain that way
     */
    @JsName("lint") fun lint(text: String): Array<LintError> {
        try {
            Assembler.assemble(text)
            return emptyArray()
        } catch (e: AssemblerError) {
            return arrayOf(LintError(e.line ?: -1, e.message ?: ""))
        }
    }
}
