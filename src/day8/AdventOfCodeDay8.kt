package day8

import utils.getLinesFromResources


class Display(val rows: Int, val columns: Int) {

    private val model = mutableSetOf<Pair<Int, Int>>()

    fun getOnPixels() = model.size

    fun displayCode() {

        for (i in 0 until rows) {
            (0 until columns)
                    .map { model.contains(Pair(i, it)) }
                    .map { if (it) 0.toChar() else ' ' }
                    .forEach(::print)
            println()
        }
    }

    private fun fillRect(x: Int, y: Int) {
        for (j in 0 until x) {
            (0 until y).mapTo(model) { Pair(it, j) }
        }
    }

    private fun rotateColumn(column: Int, rotations: Int) {
        val currentElements = (0 until rows).map { Pair(it, column) }.filter { it in model }
        model.removeAll(currentElements)
        currentElements.map { Pair((it.first + rotations) % rows, column) }.forEach { model.add(it) }
    }

    private fun rotateRow(row: Int, rotations: Int) {
        val currentElements = (0 until columns).map { Pair(row, it) }.filter { it in model }
        model.removeAll(currentElements)
        currentElements.map { Pair(row, (it.second + rotations) % columns) }.forEach { model.add(it) }
    }

    fun executeCommand(command: String) {

        if (command.startsWith("rect")) {
            val index = command.lastIndexOf(' ')
            val subString = command.substring(index)
            val items = subString.split('x')

            val x = items[0].trim().toInt()
            val y = items[1].trim().toInt()

            fillRect(x, y)
        } else {
            val index = command.lastIndexOf(' ')
            val rotationCount = command.substring(index).trim().toInt()

            val index2 = command.lastIndexOf('=')
            val index3 = command.indexOf(' ', index2)

            val item = command.substring(index2 + 1, index3).trim().toInt()

            if ("column" in command) {
                rotateColumn(item, rotationCount)
            } else
                rotateRow(item, rotationCount)
        }
    }
}

fun solution() {

    val commands = getLinesFromResources("InputDay8.txt")
    val display = Display(6, 50)

    commands.forEach { display.executeCommand(it) }

    println("Task1 ${display.getOnPixels()}")
    println("Task2")
    println(display.displayCode())
}

fun main(argc: Array<String>) {

    println("Day8:")
    solution()
}
