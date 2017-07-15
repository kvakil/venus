package venus.driver

import venus.assembler.Assembler
import venus.linker.Linker
import venus.simulator.Simulator

@JsName("Driver")
class Driver(val text: String) {
    val sim: Simulator
    init {
        val prog = Assembler.assemble(text)
        val linked = Linker.link(listOf(prog))
        sim = Simulator(linked)
        updateAll()
    }

    fun step(): Boolean {
        sim.step()
        updateAll()
        return sim.isDone()
    }

    internal fun updateAll() {
        for (i in 0..31) {
            updateRegister(i, sim.state.getReg(i))
        }
    }

    internal fun updateRegister(id: Int, value: Int) {
        val htmlId = "reg${id}"
        js("document.getElementById(htmlId).innerHTML = value;")
    }
}