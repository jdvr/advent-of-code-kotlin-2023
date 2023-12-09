fun main() {
    val dayId = "Day09"

    fun generateSubProgressions(progression: List<Long>): List<Long> {

        var all0s = true
        val subP = progression.windowed(2) {
            (l, r) ->
            val d = r - l
            // a bit hacky but I avoid  call to `.all`
            if (d != 0L) {
                all0s = false
            }
            d
        }

        if (all0s) {
            return emptyList()
        }

        return listOf(subP.last()) + generateSubProgressions(subP)
    }

    fun part1(input: List<String>): Long {
        val lastValues = input.map {
            val progression = it.toListOfLong()
            val lastValues = generateSubProgressions(progression)
            lastValues.sum() + progression.last()
        }

        return lastValues.sum()
    }

    fun part2(input: List<String>): Long {
        val firstValues = input.map {
            val progression = it.toListOfLong().reversed()
            val firstValues = generateSubProgressions(progression)
            firstValues.sum() + progression.last()
        }

        return firstValues.sum()
    }


//  test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayId}_test")
    part1(testInput).apply {
        this.println()
        check(this == 114L)
    }

    part2(testInput).apply {
        this.println()

        check(this == 2L || this == -12L)
    }

    val input = readInput(dayId)
    "running with input data".println()
    """
        Results:
            part1: ${part1(input)}
            part 2: ${part2(input)}
    """.trimIndent().println()

}
