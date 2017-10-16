package venus.riscv.insts.dsl

import venus.riscv.insts.dsl.disasms.ShiftImmediateDisassembler
import venus.riscv.insts.dsl.formats.RTypeFormat
import venus.riscv.insts.dsl.impls.NoImplementation
import venus.riscv.insts.dsl.impls.ShiftImmediateImplementation32
import venus.riscv.insts.dsl.parsers.ShiftImmediateParser

class ShiftImmediateInstruction(
        name: String,
        funct3: Int,
        funct7: Int,
        eval32: (Int, Int) -> Int,
        eval64: (Long, Long) -> Long = { _, _ -> throw NotImplementedError("no rv64") }
) : Instruction(
        name = name,
        format = RTypeFormat(
                opcode = 0b0010011,
                funct3 = funct3,
                funct7 = funct7
        ),
        parser = ShiftImmediateParser,
        impl32 = ShiftImmediateImplementation32(eval32),
        impl64 = NoImplementation,
        disasm = ShiftImmediateDisassembler
)
