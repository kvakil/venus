package venus.riscv.insts.dsl

import venus.riscv.insts.dsl.formats.ITypeFormat
import venus.riscv.insts.dsl.impls.ITypeImplementation32
import venus.riscv.insts.dsl.impls.NoImplementation
import venus.riscv.insts.dsl.parsers.ITypeParser

class ITypeInstruction(
        name: String,
        opcode: Int,
        funct3: Int,
        eval32: (Int, Int) -> Int,
        eval64: (Long, Long) -> Long = { _, _ -> throw NotImplementedError("no rv64") }
) : Instruction(
        name = name,
        format = ITypeFormat(opcode, funct3),
        parser = ITypeParser,
        impl32 = ITypeImplementation32(eval32),
        impl64 = NoImplementation
)
