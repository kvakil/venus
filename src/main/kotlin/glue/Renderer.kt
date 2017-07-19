package venus.glue

import org.w3c.dom.* // ktlint-disable no-wildcard-imports
import org.w3c.dom.css.* // ktlint-disable no-wildcard-imports

import venus.riscv.InstructionField
import venus.assembler.AssemblerError
import venus.simulator.Simulator
import venus.simulator.Diff
import venus.simulator.diffs.* // ktlint-disable no-wildcard-imports

internal object Renderer {
    private val document = Document()
    private val programTable = document.getElementById("program-listing-body") as HTMLTableElement

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
        val tabView = "$tab-tab-view"
        val tabDisplay = "$tab-tab"
        if (visible) {
            setDisplay(tabView, "block")
            setClass(tabDisplay, "is-active")
        } else {
            setDisplay(tabView, "none")
            setClass(tabDisplay, "")
        }
    }

    fun setDisplay(id: String, disp: String) {
        val ele = document.getElementById(id) as HTMLElement
        ele.style.display = disp
    }

    fun setClass(id: String, clazz: String) {
        document.getElementById(id)!!.className = clazz
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

    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    fun updateRegister(id: Int, value: Int) {
        val htmlId = "reg${id}"
        //js("document.getElementById(htmlId).innerHTML = value;")
    }
}