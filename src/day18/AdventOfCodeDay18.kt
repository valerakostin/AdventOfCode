package day18

import utils.getLinesFromResources


data class Field(val initial: String) {

    private var save: Int = 0
    private var current = ".$initial."

    init {
        save = initial.count { it == '.' }
    }

    fun getNumberOfSaveAfter(times: Int): Int {

        repeat(times) {
            val next = buildString {
                append('.')
                for (i in 1..current.length - 2) {
                    if (current[i - 1] == current[i + 1])
                        append('.')
                    else
                        append('^')
                }
                append('.')
            }

            save += next.count { it == '.' } - 2
            current = next
        }
        return save
    }
}

fun task1(line: String) {

    val field = Field(line)
    println("Task1 ${field.getNumberOfSaveAfter(40 - 1)}")
}

fun task2(line: String) {

    val field = Field(line)
    println("Task2 ${field.getNumberOfSaveAfter(400000 - 1)}")
}

fun solution() {

    val lines = getLinesFromResources("InputDay18.txt")
    val initial = lines[0]
    task1(initial)
    task2(initial)
}

fun main(argc: Array<String>) {

    println("Day18:")
    solution()
}