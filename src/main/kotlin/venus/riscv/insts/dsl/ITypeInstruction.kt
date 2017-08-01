package venus.riscv.insts.dsl

import venus.riscv.insts.dsl.formats.ITypeFormat
import venus.riscv.insts.dsl.impls.NoImplementation
import venus.riscv.insts.dsl.impls.RTypeImplementation32
import venus.riscv.insts.dsl.parsers.ITypeParser
import venus.riscv.insts.dsl.relocators.NoRelocator

class ITypeInstruction(
        name: String,
        opcode: Int,
        funct3: Int,
        eval32: (Int, Int) -> Int,
        eval64: (Long, Long) -> Long
) : Instruction(
        name = name,
        format = ITypeFormat(opcode, funct3),
        parser = ITypeParser,
        impl32 = RTypeImplementation32(eval32),
        impl64 = NoImplementation,
        relocator = NoRelocator
)
