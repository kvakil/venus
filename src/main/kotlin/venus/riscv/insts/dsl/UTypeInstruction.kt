package venus.riscv.insts.dsl

import venus.riscv.MachineCode
import venus.riscv.insts.dsl.disasms.UTypeDisassembler
import venus.riscv.insts.dsl.formats.UTypeFormat
import venus.riscv.insts.dsl.impls.RawImplementation
import venus.riscv.insts.dsl.parsers.UTypeParser
import venus.simulator.Simulator

class UTypeInstruction(
        name: String,
        opcode: Int,
        impl32: (MachineCode, Simulator) -> Unit,
        impl64: (MachineCode, Simulator) -> Unit = { _, _ -> throw NotImplementedError("no rv64") }
) : Instruction(
        name = name,
        format = UTypeFormat(opcode),
        parser = UTypeParser,
        impl32 = RawImplementation(impl32),
        impl64 = RawImplementation(impl64),
        disasm = UTypeDisassembler
)
