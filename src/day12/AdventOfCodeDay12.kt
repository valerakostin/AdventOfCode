package day12

import utils.getLinesFromResources


typealias Program = List<String>

class Processor {

    var registers = mutableMapOf<String, Int>()
    var programCounter: Int = 0

    fun executeProgram(program: Program) {

        val commands = readProgram(program)
        while (programCounter < program.size) {

            val command = commands[programCounter]

            when (command) {
                is Command.Inc -> {
                    registers[command.source] = registers.getOrDefault(command.source, 0) + 1
                    programCounter++
                }
                is Command.Dec -> {
                    registers[command.source] = registers.getOrDefault(command.source, 0) - 1
                    programCounter++
                }
                is Command.Cpy -> {
                    val value = if (command.value.isRegister()) registers.getOrDefault(command.value, 0) else command.value.toInt()
                    registers[command.source] = value
                    programCounter++
                }
                is Command.Jnz -> {

                    val value = if (command.source.isRegister()) registers.getOrDefault(command.source, 0) else command.source.toInt()
                    if (value != 0) {
                        programCounter += command.value.toInt()
                    } else {
                        programCounter++
                    }
                }
            }
        }
    }

    fun reset() {
        registers.clear()
        programCounter = 0
    }


    fun String.isRegister() = all(Char::isLetter)

    fun getRegisterValue(reg: String) = registers.getOrDefault(reg, 0)


    sealed class Command(val source: String) {

        class Inc(reg: String) : Command(reg)
        class Dec(reg: String) : Command(reg)
        class Cpy(reg: String, val value: String) : Command(reg)
        class Jnz(reg: String, val value: String) : Command(reg)
        object Nop : Command("")
    }

    companion object {

        fun readProgram(lines: List<String>): List<Command> {

            return lines.map { it.split(" ") }.map {

                when (it[0]) {
                    "inc" -> Command.Inc(it[1])
                    "dec" -> Command.Dec(it[1])
                    "cpy" -> Command.Cpy(it[2], it[1])
                    "jnz" -> Command.Jnz(it[1], it[2])
                    else -> {
                        println("Unknown command ${it[0]}")
                        Command.Nop
                    }
                }
            }
        }
    }
}

fun solution() {

    val lines = getLinesFromResources("InputDay12.txt")
    val processor = Processor()
    processor.executeProgram(lines)
    val result1 = processor.getRegisterValue("a")
    println("Task1 $result1")


    processor.reset()
    val lines2 = listOf("cpy 1 c") + lines
    processor.executeProgram(lines2)
    val result2 = processor.getRegisterValue("a")
    println("Task2 $result2")

}

fun main(argc: Array<String>) {
    solution()
}