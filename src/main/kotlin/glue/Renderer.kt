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
    private val activeRegisters = ArrayList<HTMLElement>()

    fun renderSimulator(sim: Simulator) {
        tabSetVisibility("editor", false)
        tabSetVisibility("simulator", true)
        renderProgramListing(sim)
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
            val (programName, dbg) = programDebug
            val (lineNumber, line) = dbg
            val inst = sim.linkedProgram.prog.insts[i]
            /* TODO: convert to hex */
            val code = toHex(inst.getField(InstructionField.ENTIRE))
            addToProgramListing(code, line)
        }
    }

    fun updateAll(sim: Simulator) {
        clearActiveRegisters()
        for (i in 0..31) {
            updateRegister(i, sim.getReg(i))
        }
    }

    fun updateFromDiffs(diffs: List<Diff>) {
        for (diff in diffs) {
            when (diff) {
                is RegisterDiff -> updateRegister(diff.id, diff.v, true)
                else -> {
                    println("diff not yet implemented")
                }
            }
        }
    }

    fun clearProgramListing() {
        getElement("program-listing-body").innerHTML = ""
    }

    fun addToProgramListing(code: String, progLine: String) {
        val programTable = getElement("program-listing-body") as HTMLTableSectionElement
        val newRow = programTable.insertRow() as HTMLTableRowElement
        val machineCode = newRow.insertCell(0)
        val machineCodeText = document.createTextNode(code)
        machineCode.appendChild(machineCodeText)
        val line = newRow.insertCell(1)
        val lineText = document.createTextNode(progLine)
        line.appendChild(lineText)
    }

    fun clearActiveRegisters() {
        for (register in activeRegisters) {
            register.className = ""
        }
        activeRegisters.clear()
    }

    fun getElement(id: String): HTMLElement = document.getElementById(id) as HTMLElement

    fun updateRegister(id: Int, value: Int, setActive: Boolean = false) {
        val registerValue = getElement("reg-$id-val")
        registerValue.innerHTML = toHex(value)
        if (setActive) {
            val register = getElement("reg-$id")
            register.className = "active-register"
            activeRegisters.add(register)
        }
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
