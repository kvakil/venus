package venus.assembler

/**
 * Thrown when errors occur during assembly.
 *
 * @todo split this into AssemblerUserError and AssemblerError
 */
class AssemblerError : Throwable {
    /**
     * @param msg the message to error with
     */
    constructor(msg: String? = null) : super(msg)

    /**
     * @param line the line the error occurred on
     * @param e the original error to pass along
     */
    constructor(line: Int, e: Throwable) : super("${e.message} on line ${line}", e)
}
