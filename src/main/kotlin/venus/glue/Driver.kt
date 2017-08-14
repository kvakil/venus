package venus.glue

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import venus.assembler.Assembler
import venus.assembler.AssemblerError
import venus.linker.Linker
import venus.riscv.userStringToInt
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
        runEnd()
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
        if (currentlyRunning()) {
            runEnd()
        } else {
            Renderer.setRunButtonSpinning(true)
            timer = window.setTimeout(Driver::runStart, TIMEOUT_TIME)
            sim.step() // walk past breakpoint
        }
    }

    /**
     * Resets the simulator to its initial state
     */
    @JsName("reset") fun reset() {
        openSimulator()
    }

    @JsName("toggleBreakpoint") fun addBreakpoint(idx: Int) {
        val isBreakpoint = sim.toggleBreakpointAt(idx)
        Renderer.renderBreakpointAt(idx, isBreakpoint)
    }

    internal const val TIMEOUT_CYCLES = 100
    internal const val TIMEOUT_TIME = 10
    internal fun runStart() {
        var cycles = 0
        while (cycles < TIMEOUT_CYCLES) {
            if (sim.isDone() || sim.atBreakpoint()) {
                runEnd()
                Renderer.updateAll()
                return
            }

            sim.step()
            cycles++
        }

        timer = window.setTimeout(Driver::runStart, TIMEOUT_TIME)
    }

    internal fun runEnd() {
        Renderer.setRunButtonSpinning(false)
        timer?.let(window::clearTimeout)
        timer = null
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
     * Save a register's value
     */
    @JsName("saveRegister") fun saveRegister(reg: HTMLInputElement, id: Int) {
        if (!currentlyRunning()) {
            try {
                val input = reg.value
                sim.setRegNoUndo(id, userStringToInt(input))
            } catch (e: NumberFormatException) {
                /* do nothing */
            }
        }
        Renderer.updateRegister(id, sim.getReg(id))
    }

    @JsName("updateRegMemDisplay") fun updateRegMemDisplay() {
        Renderer.updateRegMemDisplay()
    }

    @JsName("moveMemoryJump") fun moveMemoryJump() = Renderer.moveMemoryJump()

    @JsName("moveMemoryUp") fun moveMemoryUp() = Renderer.moveMemoryUp()

    @JsName("moveMemoryDown") fun moveMemoryDown() = Renderer.moveMemoryDown()

    @JsName("storeMemoryByte") fun storeMemoryByte(input: HTMLInputElement, offset: Int) =
            Renderer.storeMemoryByte(input, offset)
}
