package venus.driver

import venus.assembler.Assembler
import venus.linker.Linker
import venus.simulator.Simulator
import venus.simulator.Diff
import venus.simulator.diffs.* // ktlint-disable no-wildcard-imports

@JsName("Driver")
object Driver {
    lateinit var sim: Simulator

    @JsName("assemble")
    fun assemble(text: String) {
        val prog = Assembler.assemble(text)
        val linked = Linker.link(listOf(prog))
        sim = Simulator(linked)
        updateAll()
    }

    @JsName("step")
    fun step(): Boolean {
        val diffs = sim.step()
        updateFromDiffs(diffs)
        return sim.isDone()
    }

    @JsName("undo")
    fun undo() {
        val diffs = sim.undo()
        updateFromDiffs(diffs)
    }

    internal fun updateAll() {
        for (i in 0..31) {
            updateRegister(i, sim.getReg(i))
        }
    }

    internal fun updateFromDiffs(diffs: List<Diff>) {
        for (diff in diffs) {
            when (diff) {
                is RegisterDiff -> updateRegister(diff.id, diff.v)
                else -> {
                    println("diff not yet implemented")
                }
            }
        }
    }

    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    internal fun updateRegister(id: Int, value: Int) {
        val htmlId = "reg${id}"
        js("document.getElementById(htmlId).innerHTML = value;")
    }
}