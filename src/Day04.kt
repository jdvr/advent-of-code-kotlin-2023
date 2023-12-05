import kotlin.math.pow

fun main() {
    val dayId = "Day04"
    val regex = "(\\d+)".toRegex()


    fun extractId(cardIdRow: String): Int = regex.find(cardIdRow)
        ?.groupValues
        ?.get(0)
        ?.toInt()!!

    fun extractNumbers(text: String): List<Int> =
        regex.findAll(text)
            .map { it.groupValues[0] }
            .map { it.toInt() }
            .toList()


    fun cardMatches(numbers: String): Long {
        val (winingNumbersRaw, myNumbersRaw) = numbers.split("|")
        val winingNumbers = extractNumbers(winingNumbersRaw)
        val myNumbers = extractNumbers(myNumbersRaw)
        return myNumbers.count { winingNumbers.contains(it) }.toLong()
    }


    fun part1(input: List<String>): Long {
        var total = 0L
        for (line in input) {
            val (_, numbers) = line.splitTrim(":")
            val matches = cardMatches(numbers)
            val score = 2.0.pow(matches - 1.0).let {
                if (it < 1) {
                    0
                } else {
                    it
                }
            }.toLong()
            total += score
        }
        return total
    }

    data class Card(val id: Int, val matches: Long, val copies: Int)

    fun part2(input: List<String>): Long {
        val cards = input
            .map {
                val (cardIdRaw, numbers) = it.splitTrim(COLON)
                cardIdRaw to numbers
            }
            .groupBy({
                extractId(it.first)
            }) {
                val matches = cardMatches(it.second)
                Card(extractId(it.first), matches, 1)
            }.mapValues { it.value.first() }.toMutableMap()

        for (cardId in cards.keys) {
            val card = cards[cardId] ?: throw IllegalArgumentException("impossible")
            repeat(card.matches.toInt()) {
                cards.compute(cardId + it + 1) { _, currVal ->
                    currVal?.copy(
                        copies = currVal.copies + card.copies
                    ) ?: throw IllegalArgumentException("what?")
                }
            }
        }

        return cards.values.sumOf { it.copies }.toLong()
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
