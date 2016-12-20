package day20

import utils.getLinesFromResources
import java.util.*

data class Range(val start: Long, val end: Long) {

    fun canBeMerged(range: Range): Boolean = range.start in (start..(end + 1)) || range.end in (start..(end + 1))

    fun merge(range: Range): Range = Range(Math.min(start, range.start), Math.max(end, range.end))

    companion object {
        private val pattern = """(\d+)-(\d+)""".toRegex()

        fun toRange(str: String): Range {
            val match = pattern.matchEntire(str)
            if (match != null) {
                val (start, end) = match.destructured
                return Range(start.toLong(), end.toLong())
            } else
                throw IllegalArgumentException("Unsupported format $str")
        }
    }
}


fun solution() {

    val lines = getLinesFromResources("InputDay20.txt")

    val stack = Stack<Range>()

    lines.map { Range.toRange(it) }.sortedBy { it.start }.forEach {

        if (stack.isEmpty()) {
            stack.push(it)
        } else {
            val element = stack.peek()

            if (element.canBeMerged(it)) {
                val item = element.merge(it)
                stack.pop()
                stack.push(item)
            } else {
                stack.push(it)
            }
        }
    }

    println("Task1 ${stack[0].end + 1}")
    val sum = (0..stack.size - 2).fold(0L) { acc, index -> acc + (stack[index + 1].start - (stack[index].end + 1)) }
    println("Task2 $sum")
}

fun main(argc: Array<String>) {

    println("Day20:")
    solution()
}

