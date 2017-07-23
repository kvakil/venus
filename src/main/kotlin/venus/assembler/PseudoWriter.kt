package venus.assembler

import venus.assembler.Assembler.AssemblerState

abstract class PseudoWriter {
    internal abstract operator fun invoke(args: LineTokens,
        state: AssemblerState): List<LineTokens>
}