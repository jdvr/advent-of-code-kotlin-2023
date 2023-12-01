fun main() {
    fun part1(input: List<String>): Int =
        input
            .map {
                it.filter { char -> char.isDigit() }
            }
            .map {
                "${it.first()}${it.last()}"
            }
            .sumOf { it.toInt() }


    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    part1(testInput).apply {
        this.println()
        check(this == 142)
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
