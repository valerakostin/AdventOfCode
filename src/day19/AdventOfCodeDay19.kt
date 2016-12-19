package day19

/*
import java.util.*

private fun printPattern(f: (MutableList<Int>) -> Int) {
    for (i in 1..1000) {
        val winners = generateSequence(1) { it + 1 }.take(i).toMutableList()

        val winner = f(winners)
        println("$i  -> $winner")
    }
}

fun getWinner(participants: List<Int>): Int {
    if (participants.size == 1)
        return participants[0]

    val list = LinkedList<Int>()
    val survivors = participants.filterIndexed { index, _ -> index % 2 == 0 }.toCollection(list)

    if (participants.size % 2 == 1) {
        val last = survivors.last()
        survivors.add(0, last)
        survivors.removeAt(survivors.size - 1)
    }
    return getWinner(survivors)
}

fun getClockWinner(participants: MutableList<Int>): Int {

    var winner: Int = 0
    while (participants.size > 1) {

        winner = participants[0]

        var toRemove: Int
        if (participants.size % 2 == 0)
            toRemove = participants.size / 2
        else
            toRemove = (participants.size - 1) / 2


        participants.removeAt(toRemove)
        participants.removeAt(0)
        participants.add(winner)
    }

    return winner
}

*/
fun task1() {
    // (number - 2^a) *2 + 1
    val number = 3012210
    var i = 2

    while (i < number) {
        i *= 2
    }

    val rest = (number - i / 2)
    val result = 2 * rest + 1

    println("Task1 $result")
}


fun task2() {
// (number - 3^a)

    val number = 3012210
    var i = 3

    while (i < number) {
        i *= 3
    }

    val result = (number - i / 3)
    println("Task2 $result")

}


fun solution() {

    task1()
    task2()
}

fun main(argc: Array<String>) {

    println("Day19:")
    solution()
}

