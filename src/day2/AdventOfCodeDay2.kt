package day2

import day2.Keypad.task1
import day2.Keypad.task2
import utils.getLinesFromResources

object Keypad {
    val standardKeypad = mutableMapOf(
            "1R" to "2", "1D" to "4",
            "2L" to "1", "2R" to "3", "2D" to "5",
            "3L" to "2", "3D" to "6",
            "4U" to "1", "4R" to "5", "4D" to "9",
            "5L" to "4", "5U" to "2", "5R" to "6", "5D" to "8",
            "6L" to "5", "6U" to "3", "6D" to "9",
            "7U" to "4", "7R" to "8",
            "8L" to "7", "8U" to "5", "8R" to "9",
            "9L" to "8", "9U" to "6")

    val fancyKeypad = mutableMapOf(
            "1D" to "3",
            "2R" to "3", "2D" to "6",
            "3L" to "2", "3U" to "1", "3R" to "4", "3D" to "7",
            "4L" to "3", "4D" to "8",
            "5R" to "6",
            "6L" to "5", "6U" to "2", "6R" to "7", "6D" to "A",
            "7L" to "6", "7U" to "3", "7R" to "8", "7D" to "B",
            "8L" to "7", "8U" to "4", "8R" to "9", "8D" to "C",
            "9L" to "8",
            "AU" to "6", "AR" to "B",
            "BL" to "A", "BU" to "7", "BR" to "C", "BD" to "D",
            "CL" to "B", "CU" to "8",
            "DU" to "B")

    private fun computeCode(initial: String, sequence: String, map: Map<String, String>) = sequence.fold(initial) { position, move -> map.getOrDefault(position + move, position) }

    private fun getStandardKeypadCode(initial: String, sequence: String): String = computeCode(initial, sequence, standardKeypad)

    private fun getFancyKeypadCode(initial: String, sequence: String) = computeCode(initial, sequence, fancyKeypad)

    fun task1(input: List<String>): String = input.fold("") { acc, command -> acc + Keypad.getStandardKeypadCode(if (acc.isEmpty()) "5" else acc.last().toString(), command) }

    fun task2(input: List<String>): String = input.fold("") { acc, command -> acc + Keypad.getFancyKeypadCode(if (acc.isEmpty()) "5" else acc.last().toString(), command) }
}

fun solution() {
    val lines = getLinesFromResources("InputDay2.txt")
    println(task1(lines))
    println(task2(lines))
}

fun main(argc: Array<String>) {

    println("Day2")
    solution()
}
