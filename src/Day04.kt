import kotlin.math.pow

fun main() {
    val dayId = "Day04"

    fun cardScore(numbers: String): Long {
        val (winingNumbersRaw, myNumbersRaw) = numbers.splitTrim("|")
        val winingNumbers = " $winingNumbersRaw "
        val myNumbers = myNumbersRaw.splitTrim(" ").filter { it.isNotEmpty() && it.isNotBlank() }
        val matches = myNumbers.count { winingNumbers.contains(" $it ") }
        return 2.0.pow(matches - 1.0).let {
            if (it < 1) {
                0
            } else {
                it
            }
        }.toLong()
    }

    fun part1(input: List<String>): Long {
        var total = 0L
        for (line in input) {
            val (cardId, numbers) = line.splitTrim(":")
            val score = cardScore(numbers)
            println("$cardId worth $score")
            total += score
        }
        return total
    }


    fun part2(input: List<String>): Long = -2

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
