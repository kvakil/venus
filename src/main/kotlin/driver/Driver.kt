package venus.driver

import venus.assembler.Assembler
import venus.linker.Linker
import venus.simulator.Simulator

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
        sim.step()
        updateAll()
        return sim.isDone()
    }

    @JsName("undo")
    fun undo() {
        sim.undo()
        updateAll()
    }

    internal fun updateAll() {
        for (i in 0..31) {
            updateRegister(i, sim.getReg(i))
        }
    }

    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    internal fun updateRegister(id: Int, value: Int) {
        val htmlId = "reg${id}"
        js("document.getElementById(htmlId).innerHTML = value;")
    }
}