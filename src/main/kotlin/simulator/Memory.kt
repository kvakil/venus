package venus.simulator

class Memory {
    val memory = HashMap<Int, Byte>()
    
    fun loadByte(addr: Int): Int = memory.get(addr)?.toInt()?.and(0xff) ?: 0
    fun loadHalfWord(addr: Int): Int = (loadByte(addr + 1) shl 8) or loadByte(addr)
    fun loadWord(addr: Int): Int = (loadHalfWord(addr + 2) shl 16) or loadHalfWord(addr)

    fun storeByte(addr: Int, value: Int) = memory.put(addr, value.toByte())
    
    fun storeHalfWord(addr: Int, value: Int) {
        storeByte(addr, value)
        storeByte(addr + 1, value shr 8)
    }

    fun storeWord(addr: Int, value: Int) {
        storeHalfWord(addr, value)
        storeHalfWord(addr + 2, value shr 16)
    }
}