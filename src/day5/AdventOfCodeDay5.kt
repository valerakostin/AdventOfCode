package day5

import java.security.MessageDigest

object AdventMd5Generator {

    private val inst = MessageDigest.getInstance("MD5")
    private var currentValue = 1000000
    private val cache = mutableListOf(1000000)


    private fun next(): Int {

        if (cache.last() > currentValue)
            currentValue = cache.first { it > currentValue }
        else
            currentValue++
        return currentValue
    }

    fun nextCode(secret: String): String {


        val value = generateSequence { next() }
                .map { secret + it.toString() }
                .map { inst.digest(it.toByteArray()) }
                .map(::convertToString).filter { it != null && it.startsWith("00000") }.take(1)
                .joinToString(separator = "")

        if (currentValue !in cache)
            cache.add(currentValue)
        return value

    }

    fun reset() {
        currentValue = 1000000
    }
}

fun convertToString(bytes: ByteArray): String? {

    if (bytes[0] != 0.toByte() && bytes[1] != 0.toByte() && bytes[2] < 127)
        return null
    val sb = StringBuilder(2 * bytes.size)
    for (b in bytes) {
        sb.append(String.format("%02x", b and 0xff.toByte()))
    }

    return sb.toString()
}


fun task1() {

    print("Task 1 ")
    repeat(8) {
        print(AdventMd5Generator.nextCode("ojvtpuvg")[5])
    }
    println()
}

fun task2() {

    println("Start computation... ")
    val set = mutableSetOf<Char>()
    val password = charArrayOf('*', '*', '*', '*', '*', '*', '*', '*')
    AdventMd5Generator.reset()
    while (set.size < 8) {

        val value = AdventMd5Generator.nextCode("ojvtpuvg")
        val position = value[5]
        if (position in '0'..'7') {
            if (!set.contains(position)) {
                set.add(position)
                val index = (position.toString()).toInt()
                val v = value[6]
                password[index] = v
                println(password.joinToString(separator = ""))
            }
        }
    }
    println("Task 2  ${password.joinToString(separator = "")}")
}

fun solution() {

    task1()
    task2()
}

fun main(argc: Array<String>) {

    println("Day5:")
    solution()
}