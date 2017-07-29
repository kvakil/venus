package venus.glue

import org.w3c.dom.HTMLElement
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
    private var timer: Int? = null

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
     * Runs the simulator until it is done, or until the run button is pressed again.
     */
    @JsName("run") fun run() {
        if (timer != null) {
            runEnd()
        } else {
            Renderer.setRunButtonSpinning(true)
            timer = runStart()
        }
    }

    /**
     * Resets the simulator to its initial state
     */
    @JsName("reset") fun reset() {
        while (sim.canUndo()) {
            sim.undo()
        }
        Renderer.clearConsole()
        Renderer.updateAll()
    }

    internal const val TIMEOUT_CYCLES = 10
    internal fun runStart(): Int? {
        var cycles = 0
        while (cycles < TIMEOUT_CYCLES) {
            if (sim.isDone()) {
                runEnd()
                return null
            }

            sim.step()
            cycles++
        }

        return window.setTimeout(Driver::runStart, 20)
    }

    internal fun runEnd() {
        Renderer.setRunButtonSpinning(false)
        timer?.let(window::clearTimeout)
        timer = null
        Renderer.updateAll()
    }

    /**
     * Runs the simulator for one step and renders any updates.
     */
    @JsName("step") fun step() {
        val diffs = sim.step()
        Renderer.updateFromDiffs(diffs)
        Renderer.updateControlButtons()
    }

    /**
     * Undo the last executed instruction and render any updates.
     */
    @JsName("undo") fun undo() {
        val diffs = sim.undo()
        Renderer.updateFromDiffs(diffs)
        Renderer.updateControlButtons()
    }

    /**
     * Change to memory tab.
     */
    @JsName("openMemoryTab") fun openMemoryTab() {
        Renderer.renderMemoryTab()
    }

    /**
     * Change to register tab.
     */
    @JsName("openRegisterTab") fun openRegisterTab() {
        Renderer.renderRegisterTab()
    }

    internal fun currentlyRunning(): Boolean = timer != null

    /**
     * Make a register editable
     */
    @JsName("editRegister") @Suppress("UNUSED_PARAMETER") fun editRegister(reg: HTMLElement, id: Int) {
        if (!currentlyRunning()) reg.contentEditable = "true"
    }

    /**
     * Save a register's value
     */
    @JsName("saveRegister") fun saveRegister(reg: HTMLElement, id: Int) {
        reg.contentEditable = "false"
        if (!currentlyRunning()) {
            sim.setRegNoUndo(id, reg.innerText.toInt())
            Renderer.updateRegister(id, sim.getReg(id))
        }
    }
}
