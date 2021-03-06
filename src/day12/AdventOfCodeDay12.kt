package day12

import utils.MonorailProcessor
import utils.getLinesFromResources


fun solution() {

    val lines = getLinesFromResources("InputDay12.txt")
    val processor = MonorailProcessor()
    processor.executeProgram(lines)
    val result1 = processor.getRegisterValue("a")
    println("Task1 $result1")

    val processor2 = MonorailProcessor()
    val lines2 = listOf("cpy 1 c") + lines
    processor2.executeProgram(lines2)
    val result2 = processor2.getRegisterValue("a")
    println("Task2 $result2")
}

fun main(argc: Array<String>) {

    println("Day12:")
    solution()
}