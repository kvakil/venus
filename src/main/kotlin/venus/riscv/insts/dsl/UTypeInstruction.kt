package venus.riscv.insts.dsl

import venus.riscv.MachineCode
import venus.riscv.insts.dsl.formats.UTypeFormat
import venus.riscv.insts.dsl.impls.RawImplementation
import venus.riscv.insts.dsl.parsers.UTypeParser
import venus.riscv.insts.dsl.relocators.InstructionRelocator32
import venus.riscv.insts.dsl.relocators.InstructionRelocator64
import venus.riscv.insts.dsl.relocators.NoRelocator32
import venus.riscv.insts.dsl.relocators.NoRelocator64
import venus.simulator.Simulator

class UTypeInstruction(
        name: String,
        opcode: Int,
        impl32: (MachineCode, Simulator) -> Unit,
        impl64: (MachineCode, Simulator) -> Unit = { _, _ -> throw NotImplementedError("no rv64") },
        relocator32: InstructionRelocator32 = NoRelocator32,
        relocator64: InstructionRelocator64 = NoRelocator64
) : Instruction(
        name = name,
        format = UTypeFormat(opcode),
        parser = UTypeParser,
        impl32 = RawImplementation(impl32),
        impl64 = RawImplementation(impl64),
        relocator32 = relocator32,
        relocator64 = relocator64
)
