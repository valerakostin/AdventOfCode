package day25

import utils.MonorailProcessor
import utils.getLinesFromResources


fun task1() {

    val lines = getLinesFromResources("InputDay25.txt")
    var v = 0


    fun outputAt(str: String): Boolean {
        return str.length == 10
    }

    while (true) {
        val input = mapOf("a" to v)
        val processor = MonorailProcessor(input)
        processor.stopOutputAt = ::outputAt
        processor.executeProgram(lines)
        val output = processor.getOutput()

        if (output == "0101010101") {
            println("Task1 $v")
            break
        }
        v++
    }
}

fun solution() {

    task1()
}

fun main(argc: Array<String>) {

    println("Day25:")
    solution()
}
