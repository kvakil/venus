package venus.simulator

/* TODO: add a way to have a limit (maybe a deque?) */
class History {
    private val diffs = ArrayList<List<Diff>>()

    fun add(pre: List<Diff>) = diffs.add(pre.toList())
    fun pop() = diffs.removeAt(diffs.size - 1)
    fun isEmpty() = diffs.isEmpty()
}