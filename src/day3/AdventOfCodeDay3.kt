package day3

import utils.getLinesFromResources

fun task1(input: List<String>): Int {
    return input.map(::parseDigits).filter(::isValidTriangle).count()
}

fun task2(input: List<String>): Int {

    val all = input.map(::parseDigits)
    var count = 0
    for (i in 0..all.size - 3 step 3) {

        if (isValidTriangle(all[i].first, all[i + 1].first, all[i + 2].first))
            count++
        if (isValidTriangle(all[i].second, all[i + 1].second, all[i + 2].second))
            count++
        if (isValidTriangle(all[i].third, all[i + 1].third, all[i + 2].third))
            count++
    }
    return count
}

fun isValidTriangle(p1: Int, p2: Int, p3: Int): Boolean = p1 + p2 > p3 && p1 + p3 > p2 && p2 + p3 > p1

fun isValidTriangle(triple: Triple<Int, Int, Int>) = isValidTriangle(triple.first, triple.second, triple.third)

fun parseDigits(input: String): Triple<Int, Int, Int> {

    val str = input.trim()

    val index = str.indexOf(' ')
    val first = str.substring(0, index).toInt()

    val index2 = str.lastIndexOf(' ')
    val third = str.substring(index2).trim().toInt()

    val second = str.substring(index, index2).trim().toInt()

    val triple = Triple(first, second, third)
    return triple
}

fun solveProblem() {

    val input = getLinesFromResources("InputDay3.txt")

    val result1 = task1(input)
    println("Task1  $result1")

    val result2 = task2(input)
    println("Task2  $result2")
}

fun main(argc: Array<String>) {

    println("Day3:")
    solveProblem()
}