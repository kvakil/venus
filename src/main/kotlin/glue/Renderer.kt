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
    private val programTable: HTMLTableElement
    private val registers: ArrayList<HTMLElement>
    private val activeRegisters: ArrayList<HTMLElement>

    init {
        registers = ArrayList<HTMLElement>()
        for (id in 0..31) {
            val regId = "$id-reg"
            val register = document.getElementById(regId) as HTMLElement
            registers.add(register)
        }

        programTable = document.getElementById("program-listing-body") as HTMLTableElement

        activeRegisters = ArrayList<HTMLElement>()
    }

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
            val code = inst.getField(InstructionField.ENTIRE).toString()
            addToProgramListing(code, line)
        }
    }

    fun updateAll(sim: Simulator) {
        for (i in 0..31) {
            updateRegister(i, sim.getReg(i))
        }
    }

    fun updateFromDiffs(diffs: List<Diff>) {
        for (diff in diffs) {
            when (diff) {
                is RegisterDiff -> updateRegister(diff.id, diff.v)
                else -> {
                    println("diff not yet implemented")
                }
            }
        }
    }

    fun clearProgramListing() {
        programTable.innerHTML = ""
    }

    fun addToProgramListing(code: String, progLine: String) {
        val newRow = programTable.insertRow()
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

    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    fun updateRegister(id: Int, value: Int) {
        val htmlId = "reg${id}"
        //js("document.getElementById(htmlId).innerHTML = value;")
    }
}