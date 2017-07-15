package venus.simulator

typealias InstructionDiff = Pair<List<Diff>, List<Diff>>

class History {
    private val diffs = ArrayList<InstructionDiff>()

    fun add(pre: List<Diff>, post: List<Diff>) {
        diffs.add(InstructionDiff(pre.toList(), post.toList()))
    }

    fun peek() = diffs.last()
    fun pop() = diffs.removeAt(diffs.size - 1)
}