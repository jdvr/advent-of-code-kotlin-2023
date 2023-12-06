fun main() {
    val dayId = "Day06"

    fun part1(input: List<String>): Long {
        val times = input[0].toListOfLong()
        val distnaces = input[1].toListOfLong()

        var total = 1L

        for (index in times.indices) {
            val maxTime = times[index]
            val distance = distnaces[index]
            val possibilities = (0 until maxTime).count { pressTime ->
                val remainingTime = maxTime - pressTime
                val moved = remainingTime * pressTime
                moved > distance
            }
            println("For $maxTime and $distance there are $possibilities")
            if (possibilities != 0) {
                total *= possibilities
            }
        }
        return total
    }

    fun part2(input: List<String>): Long {
        val maxTime = input[0].filter { it.isDigit() }.toLong()
        val distance = input[1].filter { it.isDigit() }.toLong()

        return (0 until maxTime).count { pressTime ->
            val remainingTime = maxTime - pressTime
            val moved = remainingTime * pressTime
            moved > distance
        }.toLong()
    }


//  test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayId}_test")
    part1(testInput).apply {
        this.println()
        check(this == 288L)
    }

    part2(testInput).apply {
        this.println()

        check(this == 71503L || this == -12L)
    }

    val input = readInput(dayId)
    "running with input data".println()
    """
        Results:
            part1: ${part1(input)}
            part 2: ${part2(input)}
    """.trimIndent().println()

}
