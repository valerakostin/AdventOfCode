package day24

import utils.getLinesFromResources
import java.util.*


private val validCells = mutableSetOf<Loc>()
private val goalCell = mutableMapOf<Int, Loc>()
private var minimalDistance = Int.MAX_VALUE

data class Edge(val start: Int, val end: Int)

data class Loc(val x: Int, val y: Int) {
    fun getNextSteps(): List<Loc> {

        val validLocs = mutableListOf<Loc>()

        if (y - 1 >= 0) {
            val up = Loc(x, y - 1)
            if (validCells.contains(up))
                validLocs.add(up)
        }

        if (x - 1 >= 0) {
            val left = Loc(x - 1, y)
            if (validCells.contains(left))
                validLocs.add(left)
        }

        val right = Loc(x + 1, y)
        if (validCells.contains(right))
            validLocs.add(right)

        val down = Loc(x, y + 1)
        if (validCells.contains(down))
            validLocs.add(down)

        return validLocs
    }
}

private fun initialize() {

    val lines = getLinesFromResources("InputDay24.txt")

    for (i in 0 until lines.size) {
        for (j in 0 until lines[i].length) {

            val char = lines[i][j]

            if (char == '.')
                validCells.add(Loc(i, j))
            else if (char.isDigit()) {
                validCells.add(Loc(i, j))
                goalCell.put(char.toString().toInt(), Loc(i, j))
            }
        }
    }
}

private fun getAdjustments(): Map<Loc, List<Loc>> {

    val adjustment = mutableMapOf<Loc, List<Loc>>()
    validCells.forEach { adjustment.put(it, it.getNextSteps()) }
    return adjustment

}

private fun computeShortestPaths(start: Loc, adjustment: Map<Loc, List<Loc>>): Map<Loc, Int> {

    val visited = mutableSetOf<Loc>()
    val distances = mutableMapOf<Loc, Int>()

    val workSet = mutableListOf<Loc>()
    workSet.add(start)
    distances.put(start, 0)
    visited.add(start)

    while (!workSet.isEmpty()) {
        val first = workSet.removeAt(0)

        val neighbours = adjustment[first]
        if (neighbours != null) {

            for (neighbour in neighbours) {

                if (neighbour !in visited) {
                    val distance = distances.getOrDefault(first, 0)

                    val newDistance = distance + 1
                    val currentDistance = distances.getOrDefault(neighbour, Int.MAX_VALUE)

                    if (newDistance < currentDistance)
                        distances[neighbour] = newDistance

                    workSet.add(neighbour)
                }
            }
            visited.addAll(neighbours)
        }
    }
    return distances
}


private fun computeDistances(): Map<Edge, Int> {
    val adjustments = getAdjustments()
    val distances = mutableMapOf<Edge, Int>()

    for (i in 0..goalCell.size - 2) {
        val start = goalCell[i]

        if (start != null) {
            val shortDistances = computeShortestPaths(start, adjustments)
            for (j in 1 until goalCell.size) {
                val end = goalCell[j]

                if (end != null && start != end) {
                    val edge = if (i < j) Edge(i, j) else Edge(j, i)

                    if (edge !in distances) {
                        val dist = shortDistances[end]

                        if (dist != null) {
                            distances.put(edge, dist)
                        }
                    }
                }
            }
        }
    }
    return distances
}

private fun permute(items: List<Int>, startIndex: Int, cache: Map<Edge, Int>, closedPath: Boolean = false) {

    for (i in startIndex until items.size) {
        Collections.swap(items, i, startIndex)
        permute(items, startIndex + 1, cache, closedPath)
        Collections.swap(items, startIndex, i)
    }
    if (startIndex == items.size - 1) {

        val list = mutableListOf<Int>()
        list.add(0)
        list.addAll(items)
        if (closedPath)
            list.add(0)

        val length = computeLength(list, cache)
        if (length != 0) {
            minimalDistance = Math.min(length, minimalDistance)
        }
    }
}

private fun computeLength(items: List<Int>, cache: Map<Edge, Int>): Int {

    var sum = 0
    for (i in 0..items.size - 2) {

        val e1 = items[i]
        val e2 = items[i + 1]

        val edge = if (e1 < e2) Edge(e1, e2) else Edge(e2, e1)

        val result = cache[edge]
        if (result != null)
            sum += result
    }
    return sum

}

fun solution() {

    initialize()
    val list = goalCell.keys.toMutableList()
    list.remove(0)
    val distances = computeDistances()


    permute(list, 0, distances)
    println("Shortest route $minimalDistance")

    minimalDistance = Int.MAX_VALUE
    permute(list, 0, distances, true)
    println("Shortest round route $minimalDistance")
}


fun main(argc: Array<String>) {

    println("Day24:")
    solution()
}

