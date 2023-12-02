fun main() {
    val dayId = "Day02"

    var availableCubes = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val game = line.split(":")
            var gameId = game
                .first().split(" ").last().toInt()
            var sets = game.last().trim()
                .split(";")
            println("sets: $sets of game $gameId")
            val invalidSets = sets.any {
                val extractions = it.split(",").map { ex -> ex.trim() }
                println("Extractions $extractions")
                val invalidExtraction = extractions.any { ex ->
                    val (number, color) = ex.split(" ")
                    val available = availableCubes[color]
                        ?: throw IllegalArgumentException("invalid color: $color")
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
    }


    fun part2(input: List<String>): Long = -2

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
