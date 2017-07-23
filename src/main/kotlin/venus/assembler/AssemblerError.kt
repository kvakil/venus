package venus.assembler

class AssemblerError : Throwable {
    constructor(msg: String? = null) : super(msg)

    constructor(line: Int, e: Throwable) : super("${e.message} on line ${line}", e)
}