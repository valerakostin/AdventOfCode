package bonus

import day8.Display
import utils.MonorailProcessor
import utils.getLinesFromResources


private fun solution() {
    val lines = getLinesFromResources("bonuschallenge.txt")
    val process = MonorailProcessor()
    process.executeProgram(lines)
    val output = process.getStringOutput()
    val commands = output.split("\n")

    val display = Display(6, 50)

    commands.filter(String::isNotEmpty).forEach { display.executeCommand(it) }
    println(display.displayCode())
}

fun main(args: Array<String>) {
    solution()
}