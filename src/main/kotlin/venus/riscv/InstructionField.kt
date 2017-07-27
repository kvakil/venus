package venus.riscv

/** Describes how to get fields from RV32 instruction formats */
enum class InstructionField(val lo: Int, val hi: Int) {
    ENTIRE(0, 32),
    OPCODE(0, 7),
    RD(7, 12),
    FUNCT3(12, 15),
    RS1(15, 20),
    RS2(20, 25),
    FUNCT7(25, 32),
    IMM_11_0(20, 32),
    IMM_4_0(7, 12),
    IMM_11_5(25, 32),
    IMM_11_B(7, 8),
    IMM_4_1(8, 12),
    IMM_10_5(25, 31),
    IMM_12(31, 32),
    IMM_31_12(12, 32),
    IMM_19_12(12, 20),
    IMM_11_J(20, 21),
    IMM_10_1(21, 31),
    IMM_20(31, 32),
    SHAMT(20, 25),
}