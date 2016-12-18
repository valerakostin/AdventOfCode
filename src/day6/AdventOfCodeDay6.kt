package day6

import utils.getLinesFromResources
import kotlin.comparisons.compareBy
import kotlin.comparisons.thenBy

private fun computeDistribution(): MutableList<MutableMap<Char, Int>> {

    val columns = mutableListOf<MutableMap<Char, Int>>()
    repeat(8) { columns.add(mutableMapOf()) }

    val lines = getLinesFromResources("InputDay6.txt")
    for (line in lines) {
        for (position in 0..line.length - 1) {
            val char = line[position]
            val columnData = columns[position]
            columnData[char] = columnData.getOrDefault(char, 0) + 1
        }
    }
    return columns
}

fun solve() {

    val columns = computeDistribution()

    val result = columns.fold(Pair(StringBuilder(), StringBuilder())) {
        acc, dist ->
        val comparator = compareBy<Char> { dist[it] }.reversed().thenBy { it }
        val keys = dist.toSortedMap(comparator)
        acc.first.append(keys.firstKey())
        acc.second.append(keys.lastKey())
        acc
    }

    println("Task 1 " + result.first.toString())
    println("Task 2 " + result.second.toString())
}

fun main(argc: Array<String>) {

    println("Day6:")
    solve()
}