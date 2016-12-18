package day16


fun task1() {

    val size = 272
    val result = computeResult(size)

    println("Task1 $result")
}

fun task2() {

    val size = 35651584
    val result = computeResult(size)

    println("Task2 $result")
}

private fun computeResult(size: Int): String {
    val str = generateData("10001110011110000", size)
    val input = if (str.length > size) str.substring(0, size) else str
    val result = computeCheckSum(input)
    return result
}


fun getLine(a: String): String {

    val result = buildString {
        append(a)
        append('0')
        val b = a.reversed()
        b.forEach {
            val c = if (it == '0') '1' else '0'
            append(c)
        }
    }
    return result
}


fun generateData(input: String, size: Int): String {

    if (input.length < size)
        return generateData(getLine(input), size)
    return input
}

fun computeCheckSum(input: String): String {

    if (input.length % 2 == 0) {
        val str = buildString {
            for (i in 0..input.length - 2 step 2) {

                if (input[i] == input[i + 1])
                    append('1')
                else
                    append('0')
            }
        }
        return computeCheckSum(str)
    }
    return input
}


fun solution() {

    task1()
    task2()
}

fun main(argc: Array<String>) {

    println("Day16:")
    solution()
}