package day13

val magicNumber = 1352

data class Location(var x: Int, val y: Int) {

    fun isSpace(): Boolean {

        val number = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + magicNumber
        val binaryString = Integer.toBinaryString(number)
        val count = binaryString.count { it == '1' } % 2 == 0

        return count
    }

    fun getNextSteps(): List<Location> {

        val list = mutableListOf<Location>()

        if (y - 1 >= 0) {
            val up = Location(x, y - 1)
            if (up.isSpace())
                list.add(up)
        }

        if (x - 1 >= 0) {
            val left = Location(x - 1, y)
            if (left.isSpace())
                list.add(left)
        }

        val right = Location(x + 1, y)
        if (right.isSpace())
            list.add(right)

        val down = Location(x, y + 1)
        if (down.isSpace())
            list.add(down)

        return list
    }
}

data class Path(val currentPosition: Location, val visited: MutableSet<Location>) {

    fun nextPath(location: Location): Path {

        val set = mutableSetOf<Location>()
        set.addAll(visited)
        set.add(location)

        return Path(location, set)
    }
}

fun task1() {

    val start = Location(31, 39)
    val finish = Location(1, 1)

    val visited = mutableSetOf(start)
    val initialPath = Path(start, visited)
    val paths = mutableListOf(initialPath)

    while (!paths.isEmpty()) {

        val newPaths = mutableListOf<Path>()

        for (p in paths) {

            val validSteps = p.currentPosition.getNextSteps()

            validSteps
                    .filter { it !in p.visited }
                    .forEach {
                        if (it == finish) {
                            println("Task1 ${p.visited.size}")
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

fun task2() {

    val start = Location(1, 1)
    val visited = mutableSetOf(start)
    var next = mutableListOf(start)

    repeat(50) {
        next = next.flatMap(Location::getNextSteps).distinct().toMutableList()
        visited.addAll(next)
    }
    println("Task2 ${visited.size}")
}

fun solution() {

    task1()
    task2()
}

fun main(argc: Array<String>) {

    println("Day13:")
    solution()
}