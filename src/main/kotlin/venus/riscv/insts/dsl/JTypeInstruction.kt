package venus.riscv.insts.dsl

import venus.riscv.MachineCode
import venus.riscv.insts.dsl.formats.JTypeFormat
import venus.riscv.insts.dsl.impls.RawImplementation
import venus.riscv.insts.dsl.parsers.JTypeParser
import venus.riscv.insts.dsl.relocators.JALRelocator32
import venus.riscv.insts.dsl.relocators.NoRelocator64
import venus.simulator.Simulator

class JTypeInstruction(
        name: String,
        opcode: Int,
        impl32: (MachineCode, Simulator) -> Unit,
        impl64: (MachineCode, Simulator) -> Unit
) : Instruction(
        name = name,
        format = JTypeFormat(opcode),
        parser = JTypeParser,
        impl32 = RawImplementation(impl32),
        impl64 = RawImplementation(impl64),
        relocator32 = JALRelocator32,
        relocator64 = NoRelocator64
)
