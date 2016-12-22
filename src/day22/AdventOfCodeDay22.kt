package day22

import utils.getLinesFromResources


private val pattern = """/dev/grid/node-x(\d+)-y(\d+)(.*\d+)T(.*\d+)T(.*\d+)T(.*\d+)%""".toRegex()

data class Position(val x: Int, val y: Int)


data class Edge(val start: Position, val end: Position)

data class Node(val position: Position, val size: Int, val used: Int, val available: Int, val percentage: Int)


fun readNodes(): List<Node> {

    val lines = getLinesFromResources("InputDay22.txt")

    val nodes = mutableListOf<Node>()
    lines.forEach {
        val match = pattern.matchEntire(it)
        if (match != null) {
            val (locX, locY, size, used, available, percentage) = match.destructured

            val pos: Position = Position(locX.toInt(), locY.toInt())

            val ss = size.trim().toInt()
            val uu = used.trim().toInt()
            val aa = available.trim().toInt()
            val pp = percentage.trim().toInt()

            val node = Node(pos, ss, uu, aa, pp)
            nodes.add(node)
        }
    }
    return nodes
}

fun getNodeMap(nodes: List<Node>): Map<Position, Node> {

    val map = mutableMapOf<Position, Node>()
    for (node in nodes) {
        map.put(node.position, node)
    }
    return map
}

fun task1() {

    val nodes = readNodes()
    val connections = mutableSetOf<Edge>()

    for (a in nodes) {
        for (b in nodes) if (a != b && a.used > 0 && a.used <= b.available) {

            if (a.hashCode() < b.hashCode())
                connections.add(Edge(b.position, a.position))
            else
                connections.add(Edge(a.position, b.position))
        }
    }

    println("Task1 ${connections.size}")
}


fun printField() {
    val nodes = readNodes()
    val x = nodes.map { it.position.x }.max()
    val y = nodes.map { it.position.y }.max()

    if (x != null && y != null) {

        val xx = x + 1
        val yy = y + 1

        val start = Position(x, 0)
        val empty: Node? = nodes.find { it.used == 0 }

        val map = getNodeMap(nodes)
        val s = map[start]

        if (s != null && empty != null) {

            for (i in 0..yy) {
                for (j in 0..xx) {
                    val loc = Position(j, i)
                    val data = map[loc]

                    if (data != null) {
                        if (loc == start) {
                            print('D')
                        } else {
                            if (data.available >= s.used)
                                print('_')
                            else {
                                if (empty.size < data.used)
                                    print('#')
                                else
                                    print('.')
                            }
                        }
                    }
                }
                println()
            }
        }
    }
}

fun task2() {

    printField()

    val nodes = readNodes()
    val empty: Node? = nodes.find { it.used == 0 }
    if (empty != null) {
        val map = getNodeMap(nodes)

        for (i in empty.position.x downTo 0) {
            val freeWay = (empty.position.y downTo 0).map { map[Position(i, it)] }.all { it != null && empty.size >= it.used }

            if (freeWay) {
                val left = empty.position.x - i
                val up = empty.position.y
                val rowWidth = nodes.map { it.position.x }.max()!! + 1
                val right = rowWidth - (empty.position.x - left) - 1
                val stepsToStart = left + up + right
                val result = (rowWidth - 2) * 5 + stepsToStart
                println("Task2 $result")
                break
            }
        }
    }
}

fun solution() {

    task1()
    task2()
}

fun main(argc: Array<String>) {

    println("Day22:")
    solution()
}
