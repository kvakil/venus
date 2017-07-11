package venus.assembler

abstract class PseudoWriter {
    abstract operator fun invoke(args: LineTokens): List<LineTokens>
}