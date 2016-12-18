package day17

import utils.computeMD5Hash

data class Path(val location: Location, val moves: List<Char>, val passCode: String) {

    fun getPathForNewLocation(nextLocation: Location): Path {

        val allMoves = mutableListOf<Char>()
        allMoves.addAll(moves)

        val letter = location.getLetterLocation(nextLocation)
        allMoves.add(letter)

        val nextPassCode = passCode + letter

        return Path(nextLocation, allMoves, nextPassCode)
    }
}

data class Location(val x: Int, val y: Int) {

    fun isFinalDestination() = x == 3 && y == 3

    fun getLetterLocation(location: Location): Char {

        if (location.y - 1 == y) return 'D'
        else if (location.y + 1 == y) return 'U'
        else if (location.x + 1 == x) return 'L'
        else if (location.x - 1 == x) return 'R'
        else throw IllegalArgumentException("Invalid location")
    }

    fun getLocationsForCode(str: String): List<Location> {

        fun isValid(position: Int): Boolean {
            return str[position].isLetter() && str[position] != 'a'
        }

        val validMoves = mutableListOf<Location>()
        if (isValid(0) && y - 1 >= 0) {
            validMoves.add(Location(x, y - 1))
        }

        if (isValid(1) && y + 1 <= 3) {
            validMoves.add(Location(x, y + 1))
        }

        if (isValid(2) && x - 1 >= 0) {
            validMoves.add(Location(x - 1, y))
        }

        if (isValid(3) && x + 1 <= 3) {
            validMoves.add(Location(x + 1, y))
        }

        return validMoves
    }

    companion object {
        val finalLocation = Location(3, 3)
    }
}


fun compute(paths: List<Path>): String {

    if (!paths.isEmpty()) {

        val allNewPaths = mutableListOf<Path>()
        for (path in paths) {

            val nextCode = path.passCode

            val code = computeMD5Hash(nextCode)
            val newLocations = path.location.getLocationsForCode(code)

            val indexOf = newLocations.indexOf(Location.finalLocation)

            if (indexOf != -1) {
                val letter = path.location.getLetterLocation(newLocations[indexOf])

                val fullPath = path.moves.joinToString(separator = "") + letter
                return fullPath

            } else {
                val newPaths = newLocations.map {
                    path.getPathForNewLocation(it)
                }
                allNewPaths.addAll(newPaths)
            }
        }
        return compute(allNewPaths)
    }
    return ""
}

fun compute2(path: Path): Int {

    var longest: Int = 0
    val todo = mutableListOf(path)

    while (!todo.isEmpty()) {

        val newPaths = mutableListOf<Path>()

        for (p in todo) {

            val nextCode = p.passCode
            val code = computeMD5Hash(nextCode)
            val newLocations = p.location.getLocationsForCode(code)

            for (newLocation in newLocations) {

                if (newLocation.isFinalDestination()) {
                    longest = Math.max(longest, p.moves.size + 1)

                } else {
                    val newPath = p.getPathForNewLocation(newLocation)
                    newPaths.add(newPath)
                }
            }
        }
        todo.clear()
        todo.addAll(newPaths)
    }
    return longest
}


fun getInitialPath(): Path {
    val initialLocation = Location(0, 0)

    val passCode = "udskfozm"
    val initial = Path(initialLocation, mutableListOf<Char>(), passCode)
    return initial
}

fun task1() {
    val initial = getInitialPath()
    val paths = listOf(initial)
    val path = compute(paths)
    println("Task1 $path")

}

fun task2() {
    val initial = getInitialPath()
    val max = compute2(initial)
    println("Task2 $max")

}

fun solution() {

    task1()
    task2()
}

fun main(argc: Array<String>) {

    println("Day17")
    solution()
}