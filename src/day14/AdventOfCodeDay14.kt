package day14


import day5.convertToString
import java.security.MessageDigest

val cache = mutableMapOf<Int, String>()
val hashCache = mutableMapOf<String, String>()

private fun nextHash(secret: String, number: Int, stretched: Boolean = false): String {

    val result = cache[number]
    if (result != null)
        return result

    val inst = MessageDigest.getInstance("MD5")

    val secretLine = secret + number.toString()
    val byteArray = inst.digest(secretLine.toByteArray())
    var hexString = byteArray.convertToHexString()

    if (stretched)
        hexString = computeStretchedCode(hexString)

    cache[number] = hexString
    return hexString
}

private fun hasSequenceOfFive(input: String, char: Char): Boolean {
    val pattern = buildString { (0..4).forEach { append(char) } }
    return pattern in input
}


private fun ByteArray.convertToHexString(): String {
    val sb = StringBuilder(2 * this.size)
    for (b in this) {
        sb.append(String.format("%02x", b and 0xff.toByte()))
    }
    return sb.toString()
}

private fun getSequenceOfFiveData(input: String, from: Int, char: Char, stretched: Boolean = false): Pair<String, Int>? {

    val start = from + 1
    val end = start + 999

    for (i in start..end) {

        val code = nextHash(input, i, stretched)
        if (hasSequenceOfFive(code, char))
            return Pair(code, i)
    }
    return null
}


private fun clearCache(from: Int) {
    (from downTo 0).forEach { cache.remove(it) }
}

private fun computeKey(input: String, stretched: Boolean = false) {

    var count = 0
    var keyIndex = 0
    while (count < 64) {
        val code = nextHash(input, keyIndex, stretched)

        val char = has3SymbolSequence(code)
        if (char != null) {
            val five = getSequenceOfFiveData(input, keyIndex, char, stretched)
            if (five != null) {
                println("$keyIndex  -> ($count) ( $code -> ${five.first}    (${five.second}))")

                if (count == 63)
                    println("Result $keyIndex")
                clearCache(five.second)
                count++
            }
        }
        keyIndex++
    }
}

/*private fun computeStretchedCode(code: String): String {

    val inst = MessageDigest.getInstance("MD5")
    var result = code
    for (i in 0..2015) {
        val bytes = inst.digest(result.toByteArray())
        result = bytes.convertToHexString()
    }
    // do not lambda here it's to slow
    //val result = (0..2016).fold(code) { acc, _ -> inst.digest(acc.toByteArray()).convertToHexString() }
    return result
}
*/

fun computeStretchedCode(code: String): String {
    val inst = MessageDigest.getInstance("MD5")
    var result = code
    for (i in 0..2015) {
        val v = hashCache[result]
        if (v != null) {
            result = v
        } else {

            val byteArray = inst.digest(result.toByteArray())
            val vv = byteArray.convertToHexString()
            hashCache[result] = vv
            result = vv
        }
    }
    return result
}

private fun has3SymbolSequence(input: String): Char? {

    return (0..input.length - 3)
            .firstOrNull { input[it] == input[it + 1] && input[it + 1] == input[it + 2] }
            ?.let { input[it] }
}

fun task1() {
    clearCache(cache.size)
    val input = "yjdafjpo"
    computeKey(input)
}

fun task2() {
    cache.clear()
    val input = "yjdafjpo"
    computeKey(input, true)
}

fun solution() {
    task1()
    task2()
}

fun main(argc: Array<String>) {
    solution()
}
