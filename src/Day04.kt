import kotlin.math.pow

fun main() {
    val dayId = "Day04"

    fun cardMatches(numbers: String): Long {
        val (winingNumbersRaw, myNumbersRaw) = numbers.splitTrim("|")
        val winingNumbers =
            winingNumbersRaw.splitTrim(" ").filter { it.isNotEmpty() && it.isNotBlank() }
                .map { it.toInt() }
        val myNumbers = myNumbersRaw.splitTrim(" ").filter { it.isNotEmpty() && it.isNotBlank() }
            .map { it.toInt() }
        return myNumbers.count { winingNumbers.contains(it) }.toLong()
    }

    fun part1(input: List<String>): Long {
        var total = 0L
        for (line in input) {
            val (cardId, numbers) = line.splitTrim(":")
            val matches = cardMatches(numbers)
            val score = 2.0.pow(matches - 1.0).let {
                if (it < 1) {
                    0
                } else {
                    it
                }
            }.toLong()
            println("$cardId worth $score")
            total += score
        }
        return total
    }


    fun part2(input: List<String>): Long {
        var numberOfCards = 0L
        val pendingCards = mutableMapOf<String, Int>()
        val matchesByCard = mutableMapOf<String, Long>()
        val biggestCard = input.size.toLong()

        for (line in input) {
            val (cardId, numbers) = line.splitTrim(":")
            val matches = cardMatches(numbers)
            matchesByCard[cardId] = matches;
            if (matches > 0) {
                val id = cardId.splitTrim(" ").last().toInt()
                val longRange = id + 1..(id + matches).coerceAtMost(biggestCard)
                println(cardId + " with $matches generates " + longRange)
                longRange.forEach {
                    val key = "Card $it"
                    pendingCards.compute(key) { _, currValue ->
                        currValue?.plus(1) ?: 1
                    }
                }
            }

            numberOfCards++
        }
        println("going to pending cards")
        var targetCard = 2
        while (pendingCards.isNotEmpty()) {
            val key = "Card $targetCard"
            val pending = pendingCards.remove(key)
            println("post processing $key with $pending ops")
            if (pending == null) {
                targetCard++
                continue
            }
            numberOfCards += pending
            val matches = matchesByCard[key]
            if (matches == null || matches == 0L) {
                targetCard++
                continue
            }
            (targetCard + 1..(targetCard + matches).coerceAtMost(biggestCard)).forEach {
                val _key = "Card $it"
                pendingCards.compute(_key) { _, currValue ->
                    currValue?.plus(pending) ?: pending
                }
            }

            targetCard++
        }

        return numberOfCards
    }

//  test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayId}_test")
    part1(testInput).apply {
        this.println()
        check(this == 13L)
    }

    part2(testInput).apply {
        this.println()
        check(this == 30L)
    }

    val input = readInput(dayId)
    "running with input data".println()
    """
        Results:
            part1: ${part1(input)}
            part 2: ${part2(input)}
    """.trimIndent().println()

}
