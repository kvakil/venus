package venus.glue

import org.w3c.dom.HTMLTextAreaElement
import venus.assembler.Assembler
import venus.assembler.AssemblerError
import venus.linker.Linker
import venus.simulator.Simulator
import kotlin.browser.document
import kotlin.browser.window

/**
 * The "driver" singleton which can be called from Javascript for all functionality.
 */
@JsName("Driver") object Driver {
    lateinit var sim: Simulator
    var timer: Int? = null

    /**
     * Run when the user clicks the "Simulator" tab.
     *
     * Assembles the text in the editor, and then renders the simulator.
     */
    @JsName("openSimulator") fun openSimulator() {
        try {
            assemble(getText())
            Renderer.renderSimulator(sim)
        } catch (e: AssemblerError) {
            Renderer.displayError(e)
        }
    }

    /**
     * Opens and renders the editor.
     */
    @JsName("openEditor") fun openEditor() {
        Renderer.renderEditor()
    }

    /**
     * Gets the text from the textarea editor.
     */
    internal fun getText(): String {
        val editor = document.getElementById("asm-editor") as HTMLTextAreaElement
        return editor.value
    }

    /**
     * Assembles and links the program, sets the simulator
     *
     * @param text the assembly code.
     */
    internal fun assemble(text: String) {
        val prog = Assembler.assemble(text)
        val linked = Linker.link(listOf(prog))
        sim = Simulator(linked)
    }

    /**
     * Runs the simulator until it is done, rendering updates along the way.
     *
     * @todo Make this stall every once in a while to let the user stop infinite loops. (coroutines?)
     */
    @JsName("run") fun run() {
        if (timer != null) {
            runEnd()
        } else {
            Renderer.setRunActive(true)
            timer = runStart()
        }
    }

    internal fun runStart(): Int? {
        if (!sim.isDone()) {
            sim.step()
            return window.setTimeout({ runStart() }, 20)
        }
        runEnd()
        return null
    }

    internal fun runEnd() {
        Renderer.setRunActive(false)
        timer?.let { window.clearTimeout(it) }
        timer = null
        Renderer.updateAll(sim)
    }

    /**
     * Runs the simulator for one step and renders any updates.
     */
    @JsName("step") fun step() {
        val diffs = sim.step()
        Renderer.updateFromDiffs(diffs)
    }

    /**
     * Undo the last executed instruction and render any updates.
     */
    @JsName("undo") fun undo() {
        val diffs = sim.undo()
        Renderer.updateFromDiffs(diffs)
    }
}
