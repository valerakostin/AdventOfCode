package day9

import utils.getLinesFromResources


fun decompress(line: String, recursive: Boolean = false): Long {

    var current = 0
    var sum: Long = 0

    while (current < line.length) {

        val specIndexStart = line.indexOf('(', current)

        if (specIndexStart != -1) {
            val specIndexEnd = line.indexOf(')', current)

            if (specIndexEnd != -1) {

                val firstPart = line.substring(current, specIndexStart)

                val specStr = line.substring(specIndexStart, specIndexEnd + 1)
                val spec = getSpec(specStr)

                current = specIndexEnd + 1
                val compressedStr = line.substring(current, current + spec.first)

                sum += firstPart.length
                if (recursive)
                    sum += decompress(compressedStr, recursive = true) * spec.second
                else
                    sum += compressedStr.length * spec.second

                current += spec.first
            }
        } else {
            sum += line.substring(current).length
            break
        }
    }

    return sum
}

fun getSpec(str: String): Pair<Int, Int> {

    //require(str.startsWith('('))
    //require(str.endsWith(')'))

    val items = str.split('x')
    val first = items[0].substring(1).toInt()
    val second = items[1].substring(0, items[1].length - 1).toInt()

    return Pair(first, second)
}

fun solution() {

    val line = getLinesFromResources("InputDay9.txt").fold(StringBuilder(), StringBuilder::append).toString()
    val sum1 = decompress(line)
    println("Task1 $sum1")

    val sum2 = decompress(line, recursive = true)
    println("Task2 $sum2")

}

fun main(argc: Array<String>) {

    println("Day9:")
    solution()
}

