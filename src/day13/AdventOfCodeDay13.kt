package day13

import java.util.*

data class Loc(var x: Int, val y: Int) {

    private val magicNumber = 1352

    fun isValid(): Boolean {

        val value = (x * x) + (3 * x) + (2 * x * y) + y + (y * y)
        val value2 = value + magicNumber
        val vs = Integer.toBinaryString(value2)

        val count = vs.filter { it == '1' }.count() % 2 == 0
        return count
    }

    fun collectValid(): List<Loc> {

        val list = mutableListOf<Loc>()

        if (y - 1 >= 0) {
            val up = Loc(x, y - 1)
            if (up.isValid())
                list.add(up)
        }

        if (x - 1 >= 0) {
            val left = Loc(x - 1, y)
            if (left.isValid())
                list.add(left)
        }

        val right = Loc(x + 1, y)
        if (right.isValid())
            list.add(right)

        val down = Loc(x, y + 1)
        if (down.isValid())
            list.add(down)

        return list
    }
}


data class Path(val steps: Stack<Loc>, val visited: MutableSet<Loc>) {

    fun nextPath(loc: Loc): Path {

        val set = mutableSetOf<Loc>()
        set.addAll(visited)
        set.add(loc)

        val stack = Stack<Loc>()
        stack.addAll(steps)
        stack.push(loc)

        return Path(stack, set)
    }
}


fun task11() {

    val start = Loc(31, 39)
    val finish = Loc(1, 1)

    val paths = mutableListOf<Path>()

    val steps = Stack<Loc>()
    steps.push(start)

    val visited = mutableSetOf(start)
    val initialPath = Path(steps, visited)
    paths.add(initialPath)

    while (!paths.isEmpty()) {

        val newPaths = mutableListOf<Path>()

        for (p in paths) {

            val validSteps = p.steps.peek().collectValid()
            validSteps
                    .filter { it !in p.visited }
                    .forEach {
                        if (it == finish) {
                            println("Task1 ${p.steps.size}")
                            return
                        } else {

                            val newPath = p.nextPath(it)
                            newPaths.add(newPath)
                        }
                    }
        }

        paths.clear()
        paths.addAll(newPaths)

    }
}


fun task1() {

    val start = Loc(31, 39)
    val finish = Loc(1, 1)
    val path = Stack<Loc>()
    path.push(start)
    val visited = mutableSetOf(start)


    while (true) {
        val validSteps = path.peek().collectValid()
        val next: Loc? = validSteps.filter { it !in visited }.firstOrNull()

        if (next != null) {

            if (next == finish) {
                break
            }

            visited.add(next)
            path.push(next)

        } else {
            path.pop()
        }
    }

    println("Task1 ${path.size}")
}

fun task2() {

    val start = Loc(1, 1)
    val visited = mutableSetOf(start)
    var next = mutableListOf(start)

    repeat(50)
    {
        next = next.flatMap(Loc::collectValid).distinct().toMutableList()
        visited.addAll(next)
    }
    println("Task2 ${visited.size}")

}

fun solution() {
    task11()
    task2()
}

fun main(argc: Array<String>) {
    solution()
}