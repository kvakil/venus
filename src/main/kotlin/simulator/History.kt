package venus.simulator

class History {
    private val diffs = ArrayList<List<Diff>>()

    fun add(pre: List<Diff>) = diffs.add(pre.toList())
    fun peek() = diffs.last()
    fun pop() = diffs.removeAt(diffs.size - 1)
    fun isEmpty() = diffs.isEmpty()
}