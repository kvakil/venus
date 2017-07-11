package venus.assembler.pseudos

import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength
import venus.assembler.LineTokens

object NOP : PseudoWriter() {
    override operator fun invoke(args: LineTokens): List<LineTokens> {
        checkArgsLength(args, 1)
        return listOf(listOf("addi", "x0", "x0", "0"))
    }
}