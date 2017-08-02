package venus.assembler.pseudos

fun checkArgsLength(args: List<String>, required: Int) {
    if (args.size != required) throw IllegalArgumentException("wrong # of arguments")
}
