package day4

import utils.getLinesFromResources
import kotlin.comparisons.compareBy
import kotlin.comparisons.thenBy

data class RoomData(val encryptedString: String, val checkSum: String, val sectorId: Int) {

    fun isRealRoom(): Boolean {

        val histogram = encryptedString.filter { it != '-' }.fold(mutableMapOf<Char, Int>())
        { map, ch -> map.merge(ch, 1, { old, new -> if (old == null) 1 else old + new }); map }

        val comparator = compareBy<Char> { histogram[it] }.reversed().thenBy { it }
        val sortedHistogram = histogram.toSortedMap(comparator)

        val currentCheckSum = sortedHistogram.map { it.key }.take(5).joinToString(separator = "")

        return checkSum == currentCheckSum
    }

    fun decrypt(): String {

        val COUNT_OF_LETTERS = 26
        val LOWER_BOUND = 97
        val UPPER_BOUND = 123

        fun rotate(char: Char, rotate: Int): Char {

            var result = char.toInt() + rotate
            if (result >= UPPER_BOUND) {
                result = LOWER_BOUND + (result - UPPER_BOUND)
            }
            return result.toChar()
        }

        val numOfRotations = sectorId % COUNT_OF_LETTERS
        val result = encryptedString.fold(StringBuilder()) { acc, char -> if (char == '-') acc.append(" ") else acc.append(rotate(char, numOfRotations)) }.toString()

        return result
    }
}

fun parseData(rawData: String): RoomData {

    val input = rawData.trim()

    val checkSumStartBracketIndex = rawData.lastIndexOf('[')
    val checkSum = input.substring(checkSumStartBracketIndex + 1, input.lastIndex)

    val sectorDelimiterStartIndex = input.lastIndexOf('-', checkSumStartBracketIndex)
    val sectorId = input.substring(sectorDelimiterStartIndex + 1, checkSumStartBracketIndex)

    val encryptedData = rawData.substring(0, sectorDelimiterStartIndex)

    return RoomData(encryptedData, checkSum, sectorId.toInt())
}

fun solution() {

    val lines = getLinesFromResources("InputDay4.txt")
    val realRooms = lines.map(::parseData).filter { it.isRealRoom() }

    println("Task 1 ${realRooms.sumBy { it.sectorId }}")

    val result2 = realRooms.asSequence().map { Pair(it.decrypt(), it.sectorId) }.find { it.first == "northpole object storage" }?.second
    println("Task 2 $result2")
}

fun main(args: Array<String>) {
    solution()
}