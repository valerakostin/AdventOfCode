package day23

import utils.MonorailProcessor
import utils.getLinesFromResources


fun solution() {

    val lines = getLinesFromResources("InputDay23.txt")
    val processor = MonorailProcessor(mapOf("a" to 7))
    processor.executeProgram(lines)
    val result1 = processor.getRegisterValue("a")
    println("Task1 $result1")


    val lines2 = getLinesFromResources("InputDay231.txt")

    val processor2 = MonorailProcessor(mapOf("a" to 12))
    processor2.executeProgram(lines2)
    val result2 = processor2.getRegisterValue("a")
    println("Task2 $result2")

}

fun main(argc: Array<String>) {

    println("Day23:")
    solution()
}