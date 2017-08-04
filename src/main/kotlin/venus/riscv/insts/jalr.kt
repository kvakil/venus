package venus.riscv.insts

import venus.linker.relocators.NoRelocator32
import venus.linker.relocators.NoRelocator64
import venus.riscv.InstructionField
import venus.riscv.insts.dsl.Instruction
import venus.riscv.insts.dsl.formats.ITypeFormat
import venus.riscv.insts.dsl.impls.NoImplementation
import venus.riscv.insts.dsl.impls.RawImplementation
import venus.riscv.insts.dsl.impls.signExtend
import venus.riscv.insts.dsl.parsers.ITypeParser

val jalr = Instruction(
        name = "jalr",
        format = ITypeFormat(
                opcode = 0b1100111,
                funct3 = 0b000
        ),
        parser = ITypeParser,
        impl32 = RawImplementation { mcode, sim ->
            val rd = mcode[InstructionField.RD]
            val rs1 = mcode[InstructionField.RS1]
            val imm = signExtend(mcode[InstructionField.IMM_11_0], 12)
            val vrs1 = sim.getReg(rs1)
            sim.setReg(rd, sim.getPC() + mcode.length)
            sim.setPC(((vrs1 + imm) shr 1) shl 1)
        },
        impl64 = NoImplementation,
        relocator32 = NoRelocator32,
        relocator64 = NoRelocator64
)
