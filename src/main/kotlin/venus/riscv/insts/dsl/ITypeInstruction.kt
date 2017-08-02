package venus.riscv.insts.dsl

import venus.riscv.insts.dsl.formats.ITypeFormat
import venus.riscv.insts.dsl.impls.ITypeImplementation32
import venus.riscv.insts.dsl.impls.NoImplementation
import venus.riscv.insts.dsl.parsers.ITypeParser
import venus.riscv.insts.dsl.relocators.InstructionRelocator32
import venus.riscv.insts.dsl.relocators.InstructionRelocator64
import venus.riscv.insts.dsl.relocators.NoRelocator32
import venus.riscv.insts.dsl.relocators.NoRelocator64

class ITypeInstruction(
        name: String,
        opcode: Int,
        funct3: Int,
        eval32: (Int, Int) -> Int,
        eval64: (Long, Long) -> Long = { _, _ -> throw NotImplementedError("no rv64") },
        relocator32: InstructionRelocator32 = NoRelocator32,
        relocator64: InstructionRelocator64 = NoRelocator64
) : Instruction(
        name = name,
        format = ITypeFormat(opcode, funct3),
        parser = ITypeParser,
        impl32 = ITypeImplementation32(eval32),
        impl64 = NoImplementation,
        relocator32 = relocator32,
        relocator64 = relocator64
)

