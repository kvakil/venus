package venus.glue

import venus.assembler.AssemblerError
import venus.simulator.Simulator
import venus.simulator.Diff
import venus.simulator.diffs.* // ktlint-disable no-wildcard-imports

internal object Renderer {
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
        if (visible) {
            setDisplay(tab + "-tab-view", "block")
            setClass(tab + "-tab", "is-active")
        } else {
            setDisplay(tab + "-tab-view", "none")
            setClass(tab + "-tab", "")
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun setDisplay(id: String, disp: String) {
        js("document.getElementById(id).style.display = disp")
    }

    @Suppress("UNUSED_PARAMETER")
    fun setClass(id: String, clazz: String) {
        js("document.getElementById(id).className = clazz")
    }

    @Suppress("UNUSED_PARAMETER")
    fun displayError(e: AssemblerError) {
        js("alert(e.message)")
    }

    fun renderProgramListing(sim: Simulator) {
        for (programDebug in sim.linkedProgram.dbg) {
            val (programName, dbg) = programDebug
            val (lineNumber, line) = dbg
            addToProgramListing("0x00000000", line)
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

    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    fun addToProgramListing(code: String, progLine: String) {
        js("""
        var tbl = document.getElementById('program-listing-body');
        var newRow = tbl.insertRow();
        var machineCode = newRow.insertCell(0);
        var machineCodeText = document.createTextNode(code);
        machineCode.appendChild(machineCodeText);
        var line = newRow.insertCell(1);
        var lineText = document.createTextNode(progLine);
        line.appendChild(lineText);
        """)
    }

    @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
    fun updateRegister(id: Int, value: Int) {
        val htmlId = "reg${id}"
        //js("document.getElementById(htmlId).innerHTML = value;")
    }
}