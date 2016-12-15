package day15

import utils.getLinesFromResources


data class Disc(val positions: Int,
                val currentPositions: Int) {

    fun positionAt(time: Int) = (currentPositions + time) % positions

    companion object {
        private val pattern = """Disc #(\d+) has (\d+) positions; at time=0, it is at position (\d+).""".toRegex()

        fun parseDiscs(str: String): Disc {
            val match = pattern.matchEntire(str)
            if (match != null) {
                val (_, positions, currentPosition) = match.destructured
                return Disc(positions.toInt(), currentPosition.toInt())
            }
            return Disc(0, 1)
        }
    }
}

fun List<Disc>.areAreOpenedAt(time: Int): Boolean = this.mapIndexed { i, disc -> disc.positionAt(time + i) }.all { it == 0 }

fun task1() {

    val discs = getLinesFromResources("InputDay15.txt").map { Disc.parseDiscs(it) }
    val result = generateSequence(discs[0].positions) { it + 1 }.filter { discs.areAreOpenedAt(it) }.first() - 1
    println(result)
}

fun task2() {

    val discs = getLinesFromResources("InputDay15.txt").map { Disc.parseDiscs(it) }
    val discs2 = mutableListOf<Disc>()
    discs2.addAll(discs)
    discs2.add(Disc(11, 0))
    val result = generateSequence(discs[0].positions) { it + 1 }.filter { discs2.areAreOpenedAt(it) }.first() - 1
    println(result)
}


fun solution() {

    task1()
    task2()
}

fun main(argc: Array<String>) {
    solution()
}