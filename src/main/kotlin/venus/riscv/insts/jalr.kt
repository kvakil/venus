package venus.riscv.insts

import venus.assembler.AssemblerError
import venus.riscv.InstructionField
import venus.riscv.insts.dsl.Instruction
import venus.riscv.insts.dsl.disasms.RawDisassembler
import venus.riscv.insts.dsl.formats.ITypeFormat
import venus.riscv.insts.dsl.impls.NoImplementation
import venus.riscv.insts.dsl.impls.RawImplementation
import venus.riscv.insts.dsl.impls.signExtend
import venus.riscv.insts.dsl.parsers.ITypeParser
import venus.riscv.insts.dsl.parsers.LoadParser
import venus.riscv.insts.dsl.parsers.RawParser

val jalr = Instruction(
        name = "jalr",
        format = ITypeFormat(
                opcode = 0b1100111,
                funct3 = 0b000
        ),
        parser = RawParser { prog, mcode, args ->
            try {
                ITypeParser(prog, mcode, args)
            } catch (e: AssemblerError) {
                /* Try base displacement notation */
                try {
                    LoadParser(prog, mcode, args)
                } catch (e_two: AssemblerError) {
                    throw e
                }
            }
        },
        impl32 = RawImplementation { mcode, sim ->
            val rd = mcode[InstructionField.RD]
            val rs1 = mcode[InstructionField.RS1]
            val imm = signExtend(mcode[InstructionField.IMM_11_0], 12)
            val vrs1 = sim.getReg(rs1)
            sim.setReg(rd, sim.getPC() + mcode.length)
            sim.setPC(((vrs1 + imm) shr 1) shl 1)
        },
        impl64 = NoImplementation,
        disasm = RawDisassembler { mcode ->
            val rd = mcode[InstructionField.RD]
            val rs1 = mcode[InstructionField.RS1]
            val imm = signExtend(mcode[InstructionField.IMM_11_0], 12)
            "jalr x$rd x$rs1 $imm"
        }
)
