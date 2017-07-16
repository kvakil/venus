package venus.glue

import venus.assembler.Assembler
import venus.linker.Linker
import venus.simulator.Simulator

@JsName("Driver")
object Driver {
    lateinit var sim: Simulator

    @JsName("openSimulator")
    fun openSimulator() {
        assemble(getText())
        Renderer.renderSimulator(sim)
    }

    @JsName("openEditor")
    fun openEditor() {
        Renderer.renderEditor()
    }

    internal fun getText(): String {
        return js("document.getElementById('asm-editor').value")
    }

    internal fun assemble(text: String) {
        val prog = Assembler.assemble(text)
        val linked = Linker.link(listOf(prog))
        sim = Simulator(linked)
    }

    @JsName("run")
    fun run() {
        while (!sim.isDone()) {
            val diffs = sim.step()
            Renderer.updateFromDiffs(diffs)
        }
    }

    @JsName("step")
    fun step() {
        val diffs = sim.step()
        Renderer.updateFromDiffs(diffs)
    }

    @JsName("undo")
    fun undo() {
        val diffs = sim.undo()
        Renderer.updateFromDiffs(diffs)
    }
}