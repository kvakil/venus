package venus.assembler

/**
 * Thrown when errors occur during assembly.
 *
 * @todo split this into AssemblerUserError and AssemblerError
 */
class AssemblerError : Throwable {
    var line: Int? = null

    /**
     * @param msg the message to error with
     */
    constructor(msg: String? = null) : super(msg)

    /**
     * @param errorLine the line the error occurred on
     * @param e the original error to pass along
     */
    constructor(errorLine: Int, e: Throwable) : this(e.message) {
        line = errorLine
    }

    override fun toString() = if (line == null) super.toString() else "${super.toString()} on line $line"
}
