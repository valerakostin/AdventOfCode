package day21

import utils.getLinesFromResources

private val SWAP = """swap position (\d+) with position (\d+)""".toRegex()
private val SWAP_LETTERS = """swap letter ([a-z]) with letter ([a-z])""".toRegex()
private val ROTATE = """rotate (left|right) (\d+) step(.*)""".toRegex()
private val ROTATE_ON_POSITION = """rotate based on position of letter ([a-z])""".toRegex()
private val REVERSE_THROUGH = """reverse positions (\d+) through (\d+)""".toRegex()
private val MOVE = """move position (\d+) to position (\d+)""".toRegex()


sealed class Operation {

    open fun process(input: String, scrambling: Boolean = true): String {
        return input
    }

    class SwapPosition(val x: Int, val y: Int) : Operation() {
        override fun process(input: String, scrambling: Boolean): String {

            val source = if (scrambling) x else y
            val destination = if (scrambling) y else x

            val array = input.toCharArray()
            val tmp = array[source]
            array[source] = array[destination]
            array[destination] = tmp

            return array.joinToString(separator = "")
        }
    }

    class SwapLetters(val x: Char, val y: Char) : Operation() {

        override fun process(input: String, scrambling: Boolean): String {

            val source = if (scrambling) x else y
            val destination = if (scrambling) y else x

            val str = input.replace(source, '*')
            val str2 = str.replace(destination, '$')
            val str3 = str2.replace('*', destination)
            val str4 = str3.replace('$', source)

            return str4
        }
    }


    class Rotate(val dir: String, val pos: Int) : Operation() {
        override fun process(input: String, scrambling: Boolean): String {

            val direction =
                    if (!scrambling)
                        if (dir == "left") "right" else "left"
                    else
                        dir

            if ("left" == direction) {
                val l = pos % input.length
                val subString = input.substring(0 until l)
                return input.substring(l) + subString

            } else {
                val l = pos % input.length
                val subString = input.substring(input.length - l)
                return subString + input.substring(0 until input.length - l)
            }
        }
    }

    class RotatePosition(val x: Char) : Operation() {

        override fun process(input: String, scrambling: Boolean): String {

            var index = input.indexOf(x)
            if (scrambling) {

                if (index != -1) {
                    if (index >= 4)
                        index += 1
                    index += 1
                }
                return Rotate("right", index).process(input)
            } else

                if (index != -1) {
                    when (index) {
                        0 -> return Rotate("left", 1).process(input)
                        1 -> return Rotate("left", 1).process(input)
                        2 -> return Rotate("left", 6).process(input)
                        3 -> return Rotate("left", 2).process(input)
                        4 -> return Rotate("left", 7).process(input)
                        5 -> return Rotate("left", 3).process(input)
                        6 -> return Rotate("left", 0).process(input)
                        7 -> return Rotate("left", 4).process(input)
                    }
                }

            return Rotate("left", index).process(input)
        }
    }

    class Reverse(val x: Int, val y: Int) : Operation() {
        override fun process(input: String, scrambling: Boolean): String {
            val subString = input.substring(x..y).reversed()
            return input.substring(0, x) + subString + input.substring(y + 1)
        }
    }

    class Move(val x: Int, val y: Int) : Operation() {
        override fun process(input: String, scrambling: Boolean): String {
            val list = input.toMutableList()

            val source = if (scrambling) x else y
            val destination = if (scrambling) y else x

            val l = list.removeAt(source)
            list.add(destination, l)
            return list.joinToString(separator = "")
        }
    }
}


fun parseOperations(): List<Operation> {

    val lines = getLinesFromResources("InputDay21.txt")
    return lines.map(::parseOperation)
}

fun parseOperation(input: String): Operation {

    var match = SWAP.matchEntire(input)
    if (match != null) {
        val (start, end) = match.destructured
        return Operation.SwapPosition(start.toInt(), end.toInt())
    }

    match = SWAP_LETTERS.matchEntire(input)
    if (match != null) {
        val (start, end) = match.destructured
        return Operation.SwapLetters(start[0], end[0])
    }


    match = ROTATE.matchEntire(input)
    if (match != null) {
        val (dir, position) = match.destructured
        return Operation.Rotate(dir, position.toInt())
    }


    match = ROTATE_ON_POSITION.matchEntire(input)
    if (match != null) {
        val (position) = match.destructured
        return Operation.RotatePosition(position[0])
    }

    match = REVERSE_THROUGH.matchEntire(input)
    if (match != null) {
        val (x, y) = match.destructured
        return Operation.Reverse(x.toInt(), y.toInt())
    }


    match = MOVE.matchEntire(input)
    if (match != null) {
        val (x, y) = match.destructured
        return Operation.Move(x.toInt(), y.toInt())
    }

    throw IllegalArgumentException("Unknown command $input")
}

fun task1() {

    val list = parseOperations()

    var input = "abcdefgh"
    list.forEach {
        input = it.process(input)
    }
    println(input)
}


fun task2() {
    var input = "fbgdceah"
    parseOperations().reversed().forEach {
        input = it.process(input, false)
    }
    println(input)
}

fun solution() {

    task1()
    task2()
}

fun main(argc: Array<String>) {

    println("Day21:")
    solution()
}
