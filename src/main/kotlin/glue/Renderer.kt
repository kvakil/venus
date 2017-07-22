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

internal object Renderer {
    private var activeRegister: HTMLElement? = null
    private var activeInstruction: HTMLElement? = null

    fun renderSimulator(sim: Simulator) {
        tabSetVisibility("editor", false)
        tabSetVisibility("simulator", true)
        renderProgramListing(sim)
        clearConsole()
        updateAll(sim)
    }

    fun renderEditor() {
        tabSetVisibility("simulator", false)
        tabSetVisibility("editor", true)
    }

    fun tabSetVisibility(tab: String, visible: Boolean) {
        val tabView = document.getElementById("$tab-tab-view") as HTMLElement
        val tabDisplay = document.getElementById("$tab-tab") as HTMLElement
        tabView.style.display = if (visible) "block" else "none"
        tabDisplay.className = if (visible) "is-active" else ""
    }

    fun setDisplay(id: String, disp: String) {
        val ele = document.getElementById(id) as HTMLElement
        ele.style.display = disp
    }

    @Suppress("UNUSED_PARAMETER")
    fun displayError(e: AssemblerError) {
        js("alert(e.message)")
    }

    fun renderProgramListing(sim: Simulator) {
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

    fun updateAll(sim: Simulator) {
        updatePC(0)
        for (i in 0..31) {
            updateRegister(i, sim.getReg(i))
        }
    }

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

    fun clearProgramListing() {
        getElement("program-listing-body").innerHTML = ""
    }

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

    fun getElement(id: String): HTMLElement = document.getElementById(id) as HTMLElement

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

    fun updatePC(pc: Int) {
        /* TODO: abstract away instruction length */
        val idx = pc / 4
        activeInstruction?.className = ""
        val newActiveInstruction = document.getElementById("instruction-$idx") as HTMLElement?
        newActiveInstruction?.className = "is-selected"
        newActiveInstruction?.scrollIntoView(false)
        activeInstruction = newActiveInstruction
    }

    internal fun printConsole(line: Any) {
        val console = getElement("console-output") as HTMLTextAreaElement
        console.value += line.toString()
    }

    fun clearConsole() {
        val console = getElement("console-output") as HTMLTextAreaElement
        console.value = ""
    }

    /* TODO: move this? make it more efficient? */
    private val hexMap = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f')
    private fun toHex(value: Int): String {
        var remainder = value.toLong()
        var suffix = ""
        if (remainder < 0) {
            remainder += 0x1_0000_0000L
        }
        while (remainder > 0) {
            val hexDigit = hexMap[(remainder % 16).toInt()]
            suffix = hexDigit + suffix
            remainder /= 16
        }
        while (suffix.length < 8) {
            suffix = "0" + suffix
        }
        return "0x" + suffix
    }
}
