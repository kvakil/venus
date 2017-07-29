package venus.glue
/* ktlint-disable no-wildcard-imports */

import org.w3c.dom.*
import venus.assembler.AssemblerError
import venus.riscv.InstructionField
import venus.simulator.Diff
import venus.simulator.Simulator
import venus.simulator.diffs.MemoryDiff
import venus.simulator.diffs.PCDiff
import venus.simulator.diffs.RegisterDiff
import kotlin.browser.document

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
    /** The memory location currently centered */
    private var activeMemoryAddress: Int = 0
    /** The simulator being rendered */
    private lateinit var sim: Simulator

    /**
     * Shows the simulator tab and hides other tabs
     *
     * @param displaySim the simulator to show
     */
    fun renderSimulator(displaySim: Simulator) {
        tabSetVisibility("simulator", "block")
        tabSetVisibility("editor", "none")
        sim = displaySim
        setRunButtonSpinning(false)
        renderProgramListing()
        clearConsole()
        updateAll()
    }

    /** Shows the editor tab and hides other tabs */
    fun renderEditor() {
        tabSetVisibility("simulator", "none")
        tabSetVisibility("editor", "block")
    }

    /**
     * Sets the tab to the desired visiblity.
     *
     * Also updates the highlighted tab at the top.
     *
     * @param tab the name of the tab (currently "editor" or "simulator")
     */
    private fun tabSetVisibility(tab: String, display: String) {
        val tabView = document.getElementById("$tab-tab-view") as HTMLElement
        val tabDisplay = document.getElementById("$tab-tab") as HTMLElement
        tabView.style.display = display
        if (display == "none") {
            tabDisplay.classList.remove("is-active")
        } else {
            tabDisplay.classList.add("is-active")
        }
    }

    /** Display a given [AssemblerError] */
    @Suppress("UNUSED_PARAMETER") fun displayError(e: AssemblerError) {
        js("alert(e.message)")
    }

    /**
     * Renders the program listing under the debugger
     */
    private fun renderProgramListing() {
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
     */
    fun updateAll() {
        updatePC(sim.getPC())
        updateMemory(0)
        updateControlButtons()
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
                is MemoryDiff -> updateMemory(diff.addr)
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
     * @param pc the new PC
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

    /**
     * Sets whether the run button is spinning.
     *
     * @param spinning whether the button should be spin
     */
    fun setRunButtonSpinning(spinning: Boolean) {
        val runButton = getElement("simulator-run")
        if (spinning) {
            runButton.classList.add("is-loading")
            disableControlButtons()
        } else {
            runButton.classList.remove("is-loading")
            updateControlButtons()
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
     */
    fun updateControlButtons() {
        setButtonDisabled("simulator-reset", !sim.canUndo())
        setButtonDisabled("simulator-undo", !sim.canUndo())
        setButtonDisabled("simulator-step", sim.isDone())
        setButtonDisabled("simulator-run", sim.isDone())
    }

    /**
     * Disables the step, undo and reset buttons.
     *
     * Used while running, see [Driver.runStart].
     */
    fun disableControlButtons() {
        setButtonDisabled("simulator-reset", true)
        setButtonDisabled("simulator-undo", true)
        setButtonDisabled("simulator-step", true)
    }

    /**
     * Number of rows to show around the current address
     */
    const val MEMORY_CONTEXT = 6

    /** Show the memory sidebar tab */
    fun renderMemoryTab() {
        tabSetVisibility("memory", "table")
        tabSetVisibility("register", "none")
    }

    /** Show the register sidebar tab */
    fun renderRegisterTab() {
        tabSetVisibility("register", "table")
        tabSetVisibility("memory", "none")
    }

    /**
     * Update the [MEMORY_CONTEXT] words above and below the given address.
     *
     * Does not shift the memory display if it can be avoided
     *
     * @param addr the address to update around
     */
    private fun updateMemory(addr: Int) {
        val wordAddress = (addr shr 2) shl 2
        if (mustMoveMemoryDisplay(wordAddress)) {
            activeMemoryAddress = wordAddress
        }

        for (rowIdx in -MEMORY_CONTEXT..MEMORY_CONTEXT) {
            val row = getElement("mem-row-$rowIdx")
            val rowAddr = activeMemoryAddress + 4 * rowIdx
            renderMemoryRow(row, rowAddr)
        }
    }

    /**
     * Determines if we need to move the memory display to show the address
     *
     * @param wordAddress the address we want to show
     * @return true if we need to move the display
     */
    private fun mustMoveMemoryDisplay(wordAddress: Int) =
            (activeMemoryAddress - wordAddress) shr 2 !in -MEMORY_CONTEXT..MEMORY_CONTEXT

    /**
     * Renders a row of the memory.
     *
     * @param row the HTML element of the row to render
     * @param rowAddr the new address of that row
     */
    private fun renderMemoryRow(row: HTMLElement, rowAddr: Int) {
        val tdAddress = row.childNodes[0] as HTMLTableCellElement
        if (rowAddr >= 0) {
            tdAddress.innerText = toHex(rowAddr)
            for (i in 1..4) {
                val tdByte = row.childNodes[i] as HTMLTableCellElement
                tdByte.innerText = byteToHex(sim.loadByte(rowAddr + i - 1))
            }
        } else {
            tdAddress.innerText = "----------"
            for (i in 1..4) {
                val tdByte = row.childNodes[i] as HTMLTableCellElement
                tdByte.innerText = "--"
            }
        }
    }

    /** a map from integers to the corresponding hex digits */
    private val hexMap = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f')

    /**
     * Convert a certain byte to hex
     *
     * @param b the byte to convert
     * @return a hex string for the byte
     *
     * @throws IndexOutOfBoundsException if b is not in -127..255
     */
    private fun byteToHex(b: Int): String {
        val leftNibble = hexMap[b ushr 4]
        val rightNibble = hexMap[b and 15]
        return "$leftNibble$rightNibble"
    }

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
