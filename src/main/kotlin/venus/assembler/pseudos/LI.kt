package venus.assembler.pseudos

import venus.assembler.AssemblerPassOne
import venus.assembler.AssemblerError
import venus.assembler.LineTokens
import venus.assembler.PseudoWriter
import venus.riscv.userStringToInt

/**
 * Writes pseudoinstruction `li rd, imm`.
 *
 * This either expands to an `addi` if `imm` is small or a `lui` / `addi` pair if `imm` is big.
 */
object LI : PseudoWriter() {
    override operator fun invoke(args: LineTokens, state: AssemblerPassOne): List<LineTokens> {
        checkArgsLength(args, 3)
        val imm = try {
            userStringToInt(args[2])
        } catch (e: NumberFormatException) {
            throw AssemblerError("immediate to li too large or NaN")
        }

        if (imm in -2048..2047) {
            return listOf(listOf("addi", args[1], "x0", args[2]))
        } else {
            val imm_hi = (imm + 0x800) ushr 12
            val imm_lo = imm - (imm_hi shl 12)
            val lui = listOf("lui", args[1], imm_hi.toString())
            val addi = listOf("addi", args[1], args[1], imm_lo.toString())
            return listOf(lui, addi)
        }
    }
}