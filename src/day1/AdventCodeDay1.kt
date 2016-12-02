package day1

import utils.getLinesFromResources

enum class Turn {LEFT, RIGHT;

    fun getTurnFor(c: Char): Turn = if (c == 'R') RIGHT else LEFT
}

data class Move(val turn: Turn, val num: Int) {
    object companion {

        fun parseMoves(): List<Move> {
            return getLinesFromResources("InputDay1.txt").flatMap { it.split(',') }.map(String::trim)
                    .map { Move(Turn.LEFT.getTurnFor(it[0]), it.substring(1).toInt()) }.toList()
        }
    }
}

data class Point(val x: Int, val y: Int)


enum class Direction { NORTH, EAST, WEST, SOUTH;

    fun getDirection(turn: Turn): Triple<Direction, Int, Int> {
        return when (this) {
            NORTH ->
                return if (turn == Turn.RIGHT) Triple(EAST, 1, 0) else Triple(WEST, -1, 0)
            SOUTH -> return if (turn == Turn.RIGHT) Triple(WEST, -1, 0) else Triple(EAST, 1, 0)
            EAST -> return if (turn == Turn.RIGHT) Triple(SOUTH, 0, -1) else Triple(NORTH, 0, 1)
            WEST -> return if (turn == Turn.RIGHT) Triple(NORTH, 0, 1) else Triple(SOUTH, 0, -1)
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}


data class Position(val initial: Point, val direction: Direction) {

    fun move(m: Move): Position {
        val directionSpecification = direction.getDirection(m.turn)
        val point = Point(initial.x + directionSpecification.second * m.num, initial.y + directionSpecification.third * m.num)
        return Position(point, directionSpecification.first)
    }

    fun moves(m: Move): List<Position> {
        val directionSpecification = direction.getDirection(m.turn)

        return (1..m.num).map { Point(initial.x + directionSpecification.second * it, initial.y + directionSpecification.third * it) }
                .map { Position(it, directionSpecification.first) }
    }

    fun getDistance(from: Point): Int {
        return Math.abs(initial.x - from.x) + Math.abs(initial.y - from.y)
    }
}

private fun task1(initialPosition: Position, moves: List<Move>) {
    val result = moves.fold(initialPosition, Position::move)

    println("Task1 ${result.getDistance(initialPosition.initial)}")
}

private fun task2(initialPosition: Position, moves: List<Move>) {
    val cache = mutableSetOf(initialPosition.initial)
    var currentPosition = initialPosition
    for (move in moves) {
        var positions = currentPosition.moves(move)
        for (p in positions) {

            currentPosition = p
            if (currentPosition.initial in cache) {
                println("Task2 ${currentPosition.getDistance(initialPosition.initial)}")
                return
            }
            cache.add(currentPosition.initial)
        }
    }
}

private fun solve() {
    val moves = Move.companion.parseMoves()
    val initialPosition = Position(Point(0, 0), Direction.NORTH)

    task1(initialPosition, moves)
    task2(initialPosition, moves)
}

fun main(argc: Array<String>) {
    solve()
}


