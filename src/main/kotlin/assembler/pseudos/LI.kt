package venus.assembler.pseudos

import venus.assembler.PseudoWriter
import venus.assembler.writers.checkArgsLength
import venus.assembler.LineTokens
import venus.assembler.Assembler.AssemblerState
import venus.assembler.AssemblerError

object LI : PseudoWriter() {
    internal override operator fun invoke(args: LineTokens,
        state: AssemblerState): List<LineTokens> {
        checkArgsLength(args, 2)
        val imm = try {
            args[1].toLong().toInt()
        } catch (e: NumberFormatException) {
            throw AssemblerError("immediate to li too large or NaN")
        }

        if (imm in -2048..2047) {
            return listOf(listOf("addi", args[0], "x0", args[1]))
        } else {
            var imm_hi = imm ushr 12
            var imm_lo = imm and 0b111111111111
            if (imm_lo > 2047) {
                imm_lo -= 4096
                imm_hi += 1
                if (imm_hi == 1048576) imm_hi = 0
            }
            val lui = listOf("lui", args[0], imm_hi.toString())
            val addi = listOf("addi", args[0], args[0], imm_lo.toString())
            return listOf(lui, addi)
        }
    }
}