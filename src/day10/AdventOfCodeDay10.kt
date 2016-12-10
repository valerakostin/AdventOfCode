package day10

import utils.getLinesFromResources

data class Command(val botId: Int, val lower: Pair<String, Int>, val upper: Pair<String, Int>)

class Bot {

    var low: Int? = null
    var high: Int? = null

    fun addInput(value: Int) {

        if (low == null && high == null) {
            high = value
        } else {
            if (low != null) {
                if (low!! > value) {
                    high = low
                    low = value
                } else {
                    high = value
                }
            } else {

                if (high!! < value) {
                    low = high
                    high = value
                } else {
                    low = value
                }
            }
        }
    }

    fun isReady() = low != null && high != null
}


object BotFactory {

    private val setValuePattern = """value (\d+) goes to bot (\d+)""".toRegex()
    private val commandPattern = """bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""".toRegex()

    val bots = mutableMapOf<Int, Bot>()
    val output = mutableMapOf<Int, Int>()
    val commands = mutableListOf<Command>()

    private fun initInputs() {
        val lines = getLinesFromResources("InputDay10.txt")

        for (line in lines) {

            val matchResult = setValuePattern.matchEntire(line)
            if (matchResult != null) {
                val (value, botId) = matchResult.destructured

                val botIdInt = botId.toInt()
                val bot = bots.getOrDefault(botIdInt, Bot())
                bot.addInput(value.toInt())
                bots.putIfAbsent(botIdInt, bot)

            } else {
                val commandMatchResult = commandPattern.matchEntire(line)
                if (commandMatchResult != null) {
                    val (bot, lowDestination, low, highDestination, high) = commandMatchResult.destructured

                    val botId = bot.toInt()
                    val command = Command(botId, Pair(lowDestination, low.toInt()), Pair(highDestination, high.toInt()))
                    commands.add(command)
                } else
                    throw IllegalArgumentException("Unknown string format: $line")
            }
        }
    }

    private fun processValue(command: Pair<String, Int>, value: Int) {

        val destination = command.first

        if (destination == "output") {
            output.put(command.second, value)
        } else {
            val bot = bots.getOrDefault(command.second, Bot())
            bot.addInput(value)
            bots.putIfAbsent(command.second, bot)
        }
    }

    private fun processCommands() {

        while (!commands.isEmpty()) {

            val toSolve = commands.filter { (bots[it.botId]?.isReady()) ?: false }

            toSolve.forEach {
                val bot = bots[it.botId]

                if (bot != null) {

                    val lowValue = bot.low
                    val lowCommand = it.lower
                    processValue(lowCommand, lowValue!!)

                    val highValue = bot.high
                    val highCommand = it.upper
                    processValue(highCommand, highValue!!)
                }
            }
            commands.removeAll(toSolve)
        }
    }

    fun solve() {

        initInputs()
        processCommands()
    }
}

fun main(argc: Array<String>) {

    BotFactory.solve()

    val botId = BotFactory.bots.entries.filter { it.value.low == 17 && it.value.high == 61 }.first().key
    println("Task1 $botId")

    val product = BotFactory.output.getOrDefault(0, 1) * BotFactory.output.getOrDefault(1, 1) * BotFactory.output.getOrDefault(2, 1)
    println("Task2 $product")
}

