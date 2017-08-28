package venus.assembler

import venus.assembler.pseudos.checkArgsLength
import venus.riscv.MemorySegments
import venus.riscv.Program
import venus.riscv.insts.dsl.Instruction
import venus.riscv.insts.dsl.relocators.Relocator
import venus.riscv.userStringToInt

/**
 * This singleton implements a simple two-pass assembler to transform files into programs.
 */
object Assembler {
    /**
     * Assembles the given code into an unlinked Program.
     *
     * @param text the code to assemble.
     * @return an unlinked program.
     * @see venus.linker.Linker
     * @see venus.simulator.Simulator
     */
    fun assemble(text: String): AssemblerOutput {
        val (passOneProg, talInstructions, passOneErrors) = AssemblerPassOne(text).run()
        if (passOneErrors.isNotEmpty()) {
            return AssemblerOutput(passOneProg, passOneErrors)
        }
        val passTwoOutput = AssemblerPassTwo(passOneProg, talInstructions).run()
        return passTwoOutput
    }
}

data class DebugInfo(val lineNo: Int, val line: String)
data class DebugInstruction(val debug: DebugInfo, val LineTokens: List<String>)
data class PassOneOutput(
        val prog: Program,
        val talInstructions: List<DebugInstruction>,
        val errors: List<AssemblerError>
)
data class AssemblerOutput(val prog: Program, val errors: List<AssemblerError>)

/**
 * Pass #1 of our two pass assembler.
 *
 * It parses labels, expands pseudo-instructions and follows assembler directives.
 * It populations [talInstructions], which is then used by [AssemblerPassTwo] in order to actually assemble the code.
 */
internal class AssemblerPassOne(private val text: String) {
    /** The program we are currently assembling */
    private val prog = Program()
    /** The text offset where the next instruction will be written */
    private var currentTextOffset = MemorySegments.TEXT_BEGIN
    /** The data offset where more data will be written */
    private var currentDataOffset = MemorySegments.STATIC_BEGIN
    /** Whether or not we are currently in the text segment */
    private var inTextSegment = true
    /** TAL Instructions which will be added to the program */
    private val talInstructions = ArrayList<DebugInstruction>()
    /** The current line number (for user-friendly errors) */
    private var currentLineNumber = 0
    /** List of all errors encountered */
    private val errors = ArrayList<AssemblerError>()

    /**
     * Executes pass one.
     *
     * @returns the result of executing pass one.
     */
    fun run(): PassOneOutput {
        doPassOne()
        return PassOneOutput(prog, talInstructions, errors)
    }

    /**
     * Performs pass one of our assembler.
     */
    private fun doPassOne() {
        for (line in text.lines()) {
            try {
                currentLineNumber++

                val offset = getOffset()

                val (labels, args) = Lexer.lexLine(line)
                for (label in labels) {
                    val oldOffset = prog.addLabel(label, offset)
                    if (oldOffset != null) {
                        throw AssemblerError("label $label defined twice")
                    }
                }

                if (args.isEmpty() || args[0].isEmpty()) continue // empty line

                if (isAssemblerDirective(args[0])) {
                    parseAssemblerDirective(args[0], args.drop(1), line)
                } else {
                    val expandedInsts = replacePseudoInstructions(args)
                    for (inst in expandedInsts) {
                        val dbg = DebugInfo(currentLineNumber, line)
                        talInstructions.add(DebugInstruction(dbg, inst))
                        currentTextOffset += 4
                    }
                }
            } catch (e: AssemblerError) {
                errors.add(AssemblerError(currentLineNumber, e))
            }
        }
    }

    /** Gets the current offset (either text or data) depending on where we are writing */
    fun getOffset() = if (inTextSegment) currentTextOffset else currentDataOffset

    /**
     * Determines if the given token is an assembler directive
     *
     * @param cmd the token to check
     * @return true if the token is an assembler directive
     * @see parseAssemblerDirective
     */
    private fun isAssemblerDirective(cmd: String) = cmd.startsWith(".")

    /**
     * Replaces any pseudoinstructions which occur in our program.
     *
     * @param tokens a list of strings corresponding to the space delimited line
     * @return the corresponding TAL instructions (possibly unchanged)
     */
    private fun replacePseudoInstructions(tokens: LineTokens): List<LineTokens> {
        try {
            val cmd = getInstruction(tokens)
            val pw = PseudoDispatcher.valueOf(cmd).pw
            return pw(tokens, this)
        } catch (t: Throwable) {
            /* TODO: don't use throwable here */
            /* not a pseudoinstruction, or expansion failure */
            return listOf(tokens)
        }
    }

    /**
     * Changes the assembler state in response to directives
     *
     * @param directive the assembler directive, starting with a "."
     * @param args any arguments following the directive
     * @param line the original line (which is needed for some directives)
     */
    private fun parseAssemblerDirective(directive: String, args: LineTokens, line: String) {
        when (directive) {
            ".data" -> inTextSegment = false
            ".text" -> inTextSegment = true

            ".byte" -> {
                for (arg in args) {
                    val byte = userStringToInt(arg)
                    if (byte !in -127..255) {
                        throw AssemblerError("invalid byte $byte too big")
                    }
                    prog.addToData(byte.toByte())
                    currentDataOffset++
                }
            }

            ".asciiz" -> {
                checkArgsLength(args, 1)
                val ascii: String = try {
                    JSON.parse(args[0])
                } catch (e: Throwable) {
                    throw AssemblerError("couldn't parse ${args[0]} as a string")
                }
                for (c in ascii) {
                    if (c.toInt() !in 0..127) {
                        throw AssemblerError("unexpected non-ascii character: $c")
                    }
                    prog.addToData(c.toByte())
                    currentDataOffset++
                }

                /* Add NUL terminator */
                prog.addToData(0)
                currentDataOffset++
            }

            ".word" -> {
                for (arg in args) {
                    val word = userStringToInt(arg)
                    prog.addToData(word.toByte())
                    prog.addToData((word shr 8).toByte())
                    prog.addToData((word shr 16).toByte())
                    prog.addToData((word shr 24).toByte())
                    currentDataOffset += 4
                }
            }

            ".globl" -> {
                args.forEach(prog::makeLabelGlobal)
            }

            ".float", ".double", ".align" -> {
                println("Warning: $directive not currently supported!")
            }

            else -> throw AssemblerError("unknown assembler directive $directive")
        }
    }

    /**
     * Add a relocation symbol at the given offset, with the given label.
     *
     * @param relocator the [Relocator] which should be used to relocate the symbol
     * @param offset the byte offset where the relocation must be done.
     * @param label the label whose offset will later be given to the relocator.
     */
    fun addRelocation(relocator: Relocator, offset: Int, label: String) =
            prog.addRelocation(relocator, label, offset)
}

/**
 * Pass #2 of our two pass assembler.
 *
 * It writes TAL instructions to the program, and also adds debug info to the program.
 * @see addInstruction
 * @see venus.riscv.Program.addDebugInfo
 */
internal class AssemblerPassTwo(val prog: Program, val talInstructions: List<DebugInstruction>) {
    private val errors = ArrayList<AssemblerError>()
    /**
     * Executes pass two.
     *
     * @returns a program or a list of errors which occurred.
     */
    fun run(): AssemblerOutput {
        for ((dbg, inst) in talInstructions) {
            try {
                addInstruction(inst)
                prog.addDebugInfo(dbg)
            } catch (e: AssemblerError) {
                val (lineNumber, _) = dbg
                errors.add(AssemblerError(lineNumber, e))
            }
        }
        return AssemblerOutput(prog, errors)
    }

    /**
     * Adds machine code corresponding to our instruction to the program.
     *
     * @param tokens a list of strings corresponding to the space delimited line
     */
    private fun addInstruction(tokens: LineTokens) {
        if (tokens.isEmpty() || tokens[0].isEmpty()) return
        val cmd = getInstruction(tokens)
        val inst = Instruction[cmd]
        val mcode = inst.format.fill()
        inst.parser(prog, mcode, tokens.drop(1))
        prog.add(mcode)
    }
}

/**
 * Gets the instruction from a line of code
 *
 * @param tokens the tokens from the current line
 * @return the instruction (aka the first argument, in lowercase)
 */
private fun getInstruction(tokens: LineTokens) = tokens[0].toLowerCase()
