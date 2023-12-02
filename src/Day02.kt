fun main() {
    val dayId = "Day02"

    val availableCubes = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    fun part1(input: List<String>): Int = input.sumOf { line ->
        val game = line.split(COLON)
        val gameId = game.first().split(SPACE).last().toInt()
        val sets = game.last().trim().split(SEMICOLON)
        val invalidSets = sets.any {
            val extractions = it.split(COMMA).map { ex -> ex.trim() }
            val invalidExtraction = extractions.any { ex ->
                val (number, color) = ex.split(SPACE)
                val available = availableCubes[color]!!
                number.toInt() > available
            }
            invalidExtraction
        }

        if (invalidSets) {
            0
        } else {
            gameId
        }

    }


    fun part2(input: List<String>): Long = input.sumOf { line ->
        val game = line.split(COLON)
        game.last()
            .trim()
            .split(SEMICOLON)
            .map { it.trim() }
            .flatMap { it.split(COMMA) }
            .map {
                val (number, color) = it.trim().split(SPACE)
                color to number.toLong()
            }
            .groupBy({it.first}) {it.second}
            .mapValues { it.value.max()  }
            .values
            .reduce { acc, num -> acc * num }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayId}_test")
    part1(testInput).apply {
        this.println()
        check(this == 8)
    }

    part2(testInput).apply {
        this.println()
        check(this == 2286L)
    }

    val input = readInput(dayId)
    part1(input).println()
    part2(input).println()
}
