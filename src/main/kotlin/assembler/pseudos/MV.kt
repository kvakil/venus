package venus.assembler.pseudos

import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength
import venus.assembler.LineTokens

object MV : PseudoWriter() {
    override operator fun invoke(args: LineTokens): List<LineTokens> {
        checkArgsLength(args, 3)
        return listOf(listOf("addi", args[1], args[2], "0"))
    }
}