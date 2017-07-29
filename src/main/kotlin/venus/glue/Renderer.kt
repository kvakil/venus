package venus.glue
/* ktlint-disable no-wildcard-imports */
import kotlin.browser.*
import org.w3c.dom.*
import org.w3c.dom.css.*

import venus.riscv.InstructionField
import venus.assembler.AssemblerError
import venus.simulator.Simulator
import venus.simulator.Diff
import venus.simulator.diffs.*
/* ktlint-enable no-wildcard-imports */

/**
 * This singleton is used to render different parts of the screen, it serves as an interface between the UI and the
 * internal simulator.
 *
 * @todo break this up into multiple objects
 */
internal object Renderer {
    /** The register currently being highlighted */
    private var activeRegister: HTMLElement? = null
    /** The instruction currently being highlighted */
    private var activeInstruction: HTMLElement? = null

    /**
     * Shows the simulator tab and hides other tabs
     *
     * @param sim the simulator to show
     */
    fun renderSimulator(sim: Simulator) {
        tabSetVisibility("editor", false)
        tabSetVisibility("simulator", true)
        renderProgramListing(sim)
        clearConsole()
        updateAll(sim)
    }

    /** Shows the editor tab and hides other tabs */
    fun renderEditor() {
        tabSetVisibility("simulator", false)
        tabSetVisibility("editor", true)
    }

    /**
     * Sets the tab to the desired visiblity.
     *
     * Also updates the highlighted tab at the top.
     *
     * @param tab the name of the tab (currently "editor" or "simulator")
     * @param visible whether to show or hide it
     */
    private fun tabSetVisibility(tab: String, visible: Boolean) {
        val tabView = document.getElementById("$tab-tab-view") as HTMLElement
        val tabDisplay = document.getElementById("$tab-tab") as HTMLElement
        tabView.style.display = if (visible) "block" else "none"
        tabDisplay.className = if (visible) "is-active" else ""
    }

    /** Display a given [AssemblerError] */
    @Suppress("UNUSED_PARAMETER") fun displayError(e: AssemblerError) {
        js("alert(e.message)")
    }

    /**
     * Renders the program listing under the debugger
     *
     * @param sim the simulator which contains the program
     */
    private fun renderProgramListing(sim: Simulator) {
        clearProgramListing()
        for (i in 0 until sim.linkedProgram.prog.insts.size) {
            val programDebug = sim.linkedProgram.dbg[i]
            val (_, dbg) = programDebug
            val (_, line) = dbg
            val inst = sim.linkedProgram.prog.insts[i]
            val code = toHex(inst.getField(InstructionField.ENTIRE))
            addToProgramListing(i, code, line)
        }
    }

    /**
     * Refresh all of the simulator tab's content
     *
     * @param sim the simulator to use
     */
    fun updateAll(sim: Simulator) {
        updatePC(sim.getPC())
        updateControlButtons(sim)
        for (i in 0..31) {
            updateRegister(i, sim.getReg(i))
        }
    }

    /**
     * Updates the view by applying each individual diff.
     *
     * @param diffs the list of diffs to apply
     */
    fun updateFromDiffs(diffs: List<Diff>) {
        for (diff in diffs) {
            when (diff) {
                is RegisterDiff -> updateRegister(diff.id, diff.v, true)
                is PCDiff -> updatePC(diff.pc)
                else -> {
                    println("diff not yet implemented")
                }
            }
        }
    }

    /**
     * Clears the current program listing.
     *
     * @todo find a less hacky way to do this?
     */
    fun clearProgramListing() {
        getElement("program-listing-body").innerHTML = ""
    }

    /**
     * Adds an instruction with the given index to the program listing.
     *
     * @param idx the index of the instruction
     * @param code the machine code representation of the instruction
     * @param progLine the original assembly code
     */
    fun addToProgramListing(idx: Int, code: String, progLine: String) {
        val programTable = getElement("program-listing-body") as HTMLTableSectionElement
        val newRow = programTable.insertRow() as HTMLTableRowElement
        newRow.id = "instruction-$idx"
        val machineCode = newRow.insertCell(0)
        val machineCodeText = document.createTextNode(code)
        machineCode.appendChild(machineCodeText)
        val line = newRow.insertCell(1)
        val lineText = document.createTextNode(progLine)
        line.appendChild(lineText)
    }

    /**
     * Gets the element with a given ID
     *
     * @param id the id of the desired element
     *
     * @returns the HTML element corresponding to the given ID
     * @throws ClassCastException if the element is not an [HTMLElement] or does not exist
     */
    fun getElement(id: String): HTMLElement = document.getElementById(id) as HTMLElement

    /**
     * Updates the register with the given id and value.
     *
     * @param id the ID of the register (e.g., x13 has ID 13)
     * @param value the new value of the register
     * @param setActive whether the register should be set to the active register (i.e., highlighted for the user)
     */
    fun updateRegister(id: Int, value: Int, setActive: Boolean = false) {
        val registerValue = getElement("reg-$id-val")
        registerValue.innerHTML = toHex(value)
        if (setActive) {
            activeRegister?.className = ""
            val newActiveRegister = getElement("reg-$id")
            newActiveRegister.className = "is-selected"
            activeRegister = newActiveRegister
        }
    }

    /**
     * Updates the PC to the given value. It also highlights the to-be-executed instruction.
     *
     * @todo abstract away instruction length
     */
    fun updatePC(pc: Int) {
        val idx = pc / 4
        activeInstruction?.className = ""
        val newActiveInstruction = document.getElementById("instruction-$idx") as HTMLElement?
        newActiveInstruction?.className = "is-selected"
        newActiveInstruction?.scrollIntoView(false)
        activeInstruction = newActiveInstruction
    }

    /**
     * Prints the given thing to the console as a string.
     *
     * @param thing the thing to print
     */
    internal fun printConsole(thing: Any) {
        val console = getElement("console-output") as HTMLTextAreaElement
        console.value += thing.toString()
    }

    /**
     * Clears the console
     */
    fun clearConsole() {
        val console = getElement("console-output") as HTMLTextAreaElement
        console.value = ""
    }

    fun setRunActive(active: Boolean) {
        val runButton = getElement("simulator-run")
        if (active) {
            runButton.classList.add("is-active")
        } else {
            runButton.classList.remove("is-active")
        }
    }

    /**
     * Sets whether a button is disabled.
     *
     * @param id the id of the button to change
     * @param disabled whether or not to disable the button
     */
    private fun setButtonDisabled(id: String, disabled: Boolean) {
        val button = getElement(id) as HTMLButtonElement
        button.disabled = disabled
    }

    /**
     * Renders the control buttons to be enabled / disabled appropriately.
     *
     * @param sim the simulator currently being used
     */
    fun updateControlButtons(sim: Simulator) {
        setButtonDisabled("simulator-reset", !sim.canUndo())
        setButtonDisabled("simulator-undo", !sim.canUndo())
        setButtonDisabled("simulator-step", sim.isDone())
    }

    /** a map from integers to the corresponding hex digits */
    private val hexMap = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f')

    /**
     * Converts a value to a two's complement hex number.
     *
     * By two's complement, I mean that -1 becomes 0xFFFFFFFF not -0x1.
     *
     * @param value the value to convert
     * @return the hexadecimal string corresponding to that value
     * @todo move this?
     */
    private fun toHex(value: Int): String {
        var remainder = value.toLong()
        var suffix = ""

        // output as two's complement
        if (remainder < 0) {
            remainder += 0x1_0000_0000L
        }

        // convert to hex
        while (remainder > 0) {
            val hexDigit = hexMap[(remainder % 16).toInt()]
            suffix = hexDigit + suffix
            remainder /= 16
        }

        // pad with zeros if needed
        while (suffix.length < 8) {
            suffix = "0" + suffix
        }

        return "0x" + suffix
    }
}
