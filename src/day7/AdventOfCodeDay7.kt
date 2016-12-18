package day7

import utils.getLinesFromResources


class Ipv7 private constructor(seq: Array<String>, hypSeq: Array<String>) {
    private val sequence: Array<String> = seq
    private val hypernetSequence: Array<String> = hypSeq

    fun isTlsAddress(): Boolean {

        fun isTlsString(str: String) =
                (0..str.length - 4).any {
                    str[it] == str[it + 3]
                            && str[it + 1] == str[it + 2]
                            && str[it] != str[it + 1]
                }
        return sequence.any(::isTlsString) && hypernetSequence.none(::isTlsString)
    }

    fun isSslAddress(): Boolean {

        fun collectValidSslString(str: String): Set<String> {

            val validItems = (0..str.length - 3)
                    .filter { str[it] == str[it + 2] }
                    .map { buildString { append(str[it]).append(str[it + 1]).append(str[it + 2]) } }.toSet()
            return validItems
        }

        val seq = sequence.flatMap(::collectValidSslString).map {
            buildString { append(it[1]).append(it[0]).append(it[1]).toString() }
        }.toHashSet()

        val result = hypernetSequence.flatMap(::collectValidSslString).any { seq.contains(it) }

        return result
    }

    companion object {

        fun getIpV7(rawString: String): Ipv7 {

            val numberOfHypSeq = rawString.filter { it == '[' }.count()

            val seq = Array(numberOfHypSeq + 1) { "" }
            val hyp = Array(numberOfHypSeq) { "" }

            var index = 0
            var arrayIndex = 0

            while (index < rawString.length - 1) {

                val start = rawString.indexOf('[', index)

                if (start == -1) {
                    val s = rawString.substring(index, rawString.length)
                    index = rawString.length
                    seq[arrayIndex] = s
                } else {

                    val s = rawString.substring(index, start)
                    seq[arrayIndex] = s
                    val end = rawString.indexOf(']', start + 1)
                    val hs = rawString.substring(start + 1, end)
                    hyp[arrayIndex] = hs

                    arrayIndex++

                    index = end + 1
                }
            }
            return Ipv7(seq, hyp)
        }
    }
}

fun solution() {

    val ipv7s = getLinesFromResources("InputDay7.txt").map { Ipv7.getIpV7(it) }
    val count = ipv7s.filter(Ipv7::isTlsAddress).count()
    println("Task1 $count")

    val count2 = ipv7s.filter(Ipv7::isSslAddress).count()
    println("Task2 $count2")

}

fun main(argc: Array<String>) {

    println("Day2")
    solution()
}


