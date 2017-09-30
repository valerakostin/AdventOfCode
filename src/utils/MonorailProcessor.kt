package utils

typealias Program = List<String>

class MonorailProcessor(initialState: Map<String, Int> = emptyMap()) {

    private var registers = mutableMapOf<String, Int>()
    //private val sb = StringBuilder()
    private val output = mutableListOf<Byte>()

    var stopOutputAt: ((String) -> Boolean)? = null


    init {
        registers.putAll(initialState)
    }

    var programCounter: Int = 0

    fun executeProgram(program: Program) {

        val commands = readProgram(program)
        while (programCounter < program.size) {

            val command = commands[programCounter]

            // println(registers)
            // println(program[programCounter])

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
                        val value2 = if (command.value.isRegister()) registers.getOrDefault(command.value, 0) else command.value.toInt()
                        programCounter += value2
                    } else {
                        programCounter++
                    }
                }

                is Command.Tgl -> {

                    val shift = if (command.source.isRegister()) registers.getOrDefault(command.source, 0) else command.source.toInt()

                    val newCounter = programCounter + shift

                    if (newCounter < program.size) {


                        val nextCommand = commands[newCounter]

                        if (nextCommand is Command.Inc)
                            commands[newCounter] = Command.Dec(nextCommand.source)
                        else if (nextCommand is Command.Dec)
                            commands[newCounter] = Command.Inc(nextCommand.source)
                        else if (nextCommand is Command.Tgl)
                            commands[newCounter] = Command.Inc(nextCommand.source)
                        else if (nextCommand is Command.Cpy)
                            commands[newCounter] = Command.Jnz(nextCommand.value, nextCommand.source)
                        else if (nextCommand is Command.Jnz)
                            commands[newCounter] = Command.Cpy(nextCommand.value, nextCommand.source)
                    }
                    programCounter++
                }
                is Command.Nop -> {
                    programCounter++
                }
                is Command.Add -> {

                    val reg1 = if (command.regA.isRegister()) registers.getOrDefault(command.regA, 0) else command.regA.toInt()
                    val reg2 = if (command.regB.isRegister()) registers.getOrDefault(command.regB, 0) else command.regB.toInt()


                    val result = if (reg1 == 0) reg2 else reg1 + reg2

                    if (command.regA.isRegister())
                        registers[command.regA] = result
                    registers[command.regB] = 0

                    programCounter++
                }
                is Command.Mul -> {

                    val reg1 = if (command.regA.isRegister()) registers.getOrDefault(command.regA, 0) else command.regA.toInt()
                    val reg2 = if (command.regB.isRegister()) registers.getOrDefault(command.regB, 0) else command.regB.toInt()

                    val result = reg1 * reg2

                    if (command.regA.isRegister())
                        registers["a"] = result
                    registers["d"] = 0
                    registers["c"] = 0

                    programCounter++
                }
                is Command.Out -> {

                    val reg1 = if (command.regA.isRegister()) registers.getOrDefault(command.regA, 0) else command.regA.toInt()

                    output.add(reg1.toByte())


                    val result = stopOutputAt?.invoke(output.joinToString(separator = ""))
                    if (result ?: false)
                        return
                    programCounter++
                }
            }
        }
    }


    fun String.isRegister() = all(Char::isLetter)

    fun getRegisterValue(reg: String) = registers.getOrDefault(reg, 0)


    fun getOutput(): String {
        return output.joinToString(separator = "")
    }

    fun getStringOutput(): String {
        return output.map { b -> b.toChar() }.joinToString(separator = "")
    }

    sealed class Command(val source: String) {

        class Inc(reg: String) : Command(reg)
        class Dec(reg: String) : Command(reg)
        class Cpy(reg: String, val value: String) : Command(reg)
        class Jnz(reg: String, val value: String) : Command(reg)
        class Tgl(reg: String) : Command(reg)
        object Nop : Command("")
        class Add(val regA: String, val regB: String) : Command("")
        class Mul(val regA: String, val regB: String) : Command("")
        class Out(val regA: String) : Command("")
    }

    companion object {

        fun readProgram(lines: List<String>): MutableList<Command> {

            return lines.map { it.split(" ") }.map {

                when (it[0]) {
                    "inc" -> Command.Inc(it[1])
                    "dec" -> Command.Dec(it[1])
                    "cpy" -> Command.Cpy(it[2], it[1])
                    "jnz" -> Command.Jnz(it[1], it[2])
                    "tgl" -> Command.Tgl(it[1])
                    "nop" -> Command.Nop
                    "add" -> Command.Add(it[1], it[2])
                    "mul" -> Command.Mul(it[1], it[2])
                    "out" -> Command.Out(it[1])
                    else -> {
                        println("Unknown command ${it[0]}")
                        Command.Nop
                    }
                }
            }.toMutableList()
        }
    }
}
