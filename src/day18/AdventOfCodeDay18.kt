package day18

import utils.getLinesFromResources


data class Field(val initial: String) {

    private var save: Int = 0
    private var current = initial

    init {
        save = current.count { it == '.' }
    }

    fun getNumberOfSaveAfter(times: Int): Int {

        repeat(times) {
            val next = buildString {
                for (i in 0..current.length - 1) {
                    val c = current[i]

                    val r = if (i + 1 <= current.length - 1) current[i + 1] else '.'

                    val l = if ((i - 1) < 0) '.' else current[i - 1]

                    if (l == '^' && c == '^' && r == '.')
                        append('^')
                    else if (l == '.' && c == '^' && r == '^')
                        append('^')
                    else if (l == '^' && c == '.' && r == '.')
                        append('^')
                    else if (l == '.' && c == '.' && r == '^')
                        append('^')
                    else
                        append('.')
                }
            }

            save += next.count { it == '.' }
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