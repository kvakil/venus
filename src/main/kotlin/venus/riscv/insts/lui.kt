import venus.riscv.InstructionField
import venus.riscv.insts.dsl.UTypeInstruction
import venus.riscv.insts.dsl.impls.NoImplementation

val lui = UTypeInstruction(
        name = "lui",
        opcode = 0b1100111,
        impl32 = { mcode, sim ->
            val imm = mcode[InstructionField.IMM_31_12] shl 12
            sim.setReg(mcode[InstructionField.RD], imm)
            sim.incrementPC(mcode.length)
        },
        impl64 = NoImplementation::invoke
)
