package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.AssemblerError
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter

/**
 * Writes pseudoinstruction `li rd, imm`.
 *
 * This either expands to an `addi` if `imm` is small or a `lui` / `addi` pair if `imm` is big.
 */
object LI : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 3)
        val imm = try {
            args[2].toLong().toInt()
        } catch (e: NumberFormatException) {
            throw AssemblerError("immediate to li too large or NaN")
        }

        if (imm in -2048..2047) {
            return listOf(listOf("addi", args[1], "x0", args[2]))
        } else {
            var imm_hi = imm ushr 12
            var imm_lo = imm and 0b111111111111
            if (imm_lo > 2047) {
                imm_lo -= 4096
                imm_hi += 1
                if (imm_hi == 1048576) imm_hi = 0
            }
            val lui = listOf("lui", args[1], imm_hi.toString())
            val addi = listOf("addi", args[1], args[1], imm_lo.toString())
            return listOf(lui, addi)
        }
    }
}