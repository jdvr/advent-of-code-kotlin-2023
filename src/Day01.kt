val numbers = listOf(
    "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
).withIndex()

fun main() {
    fun part1(input: List<String>): Int =
        input
            .sumOf { line ->
                line.filter { it.isDigit() }
                    .let { "${it.first()}${it.last()}" }
                    .toInt()
            }


    fun part2(input: List<String>): Int = input.sumOf { line ->
        line.mapIndexedNotNull { idx, char ->
            if (char.isDigit()) {
                char
            } else {
                val pendingLine = line.slice(idx until line.length)
                numbers.find { pendingLine.startsWith(it.value) }?.index?.digitToChar()
            }
        }.let {
            "${it.first()}${it.last()}".toInt()
        }

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
