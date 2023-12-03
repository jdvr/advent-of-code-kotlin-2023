fun main() {
    val dayId = "Day03"
    val isSymbol: (Char) -> Boolean = { it != '.' && it.isDigit().not() }

    fun IntRange.neighbours(limit: Int): IntRange =
        (this.first - 1).coerceAtLeast(0)..(this.last + 1).coerceAtMost(limit - 1)

    fun String.numbers(): List<Pair<Long, IntRange>> {
        val result = mutableListOf<Pair<Long, IntRange>>()
        var onNumber = false
        var start = -2
        var read = ""
        for ((idx, char) in withIndex()) {
            when {
                char.isDigit() && !onNumber -> {
                    onNumber = true
                    start = idx
                    read += char
                }

                char.isDigit() && onNumber -> {
                    read += char
                }

                !char.isDigit() && onNumber -> {
                    val range = start until idx
                    val number = read.toLong()
                    result.add(number to range)
                    start = -2
                    read = ""
                    onNumber = false
                }
            }
        }
        if (onNumber) {
            val range = start until this.length
            val number = read.toLong()
            result.add(number to range)
        }

        return result
    }


    fun part1(input: List<String>): Long = input.mapIndexed { idx, line ->
        val numbers = line.numbers()
        val validNumbers = mutableListOf<Long>()
        val discardedNumbers = mutableListOf<Long>()
        for ((number, range) in numbers) {
            // has symbol in the same line
            if (range.first > 0 && line[range.first - 1] != '.') {
                validNumbers.add(number)
                 println("number $number has neighbour in the left")
                continue
            }
            if (range.last < line.length - 1 && line[range.last + 1] != '.') {
                validNumbers.add(number)
                 println("number $number has neighbour in the right")
                continue
            }
            // has symbol on top
            if (idx > 0) {
                val topLine = input[idx - 1]
                val topLineRange = range.neighbours(topLine.length)
                if (topLine.slice(topLineRange).any(isSymbol)) {
                     println("number $number has neighbour in the top ($topLineRange)")
                    validNumbers.add(number)
                    continue
                }
            }
            // has symbol on bottom
            if (idx < input.size - 1) {
                val bottomLine = input[idx + 1]
                val bottomLineRange = range.neighbours(bottomLine.length)
                if (bottomLine.slice(bottomLineRange).any(isSymbol)) {
                     println("number $number has neighbour in the bottom ($bottomLineRange)")
                    validNumbers.add(number)
                    continue
                }
            }
            discardedNumbers.add(number)
        }
        println("line: ${idx + 1},  has: $validNumbers, discarded: $discardedNumbers")
        validNumbers
    }.flatten().sum()


    fun part2(input: List<String>): Long = -2

//     test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayId}_test")
    part1(testInput).apply {
        this.println()
        check(this == 4361L)
    }

    part1(
        listOf(

            "12.......*..",
            "+.........34",
            ".......-12..",
            "..78........",
            "..*....60...",
            "78.........9",
            ".5.....23..\$",
            "8...90*12...",
            "............",
            "2.2......12.",
            ".*.........*",
            "1.1..503+.56"

        )
    ).apply {
        this.println()
        check(this == 925L)
    }

    part2(
        listOf(

            "12.......*..",
            "+.........34",
            ".......-12..",
            "..78........",
            "..*....60...",
            "78.........9",
            ".5.....23..\$",
            "8...90*12...",
            "............",
            "2.2......12.",
            ".*.........*",
            "1.1..503+.56"

        )
    ).apply {
        this.println()
        check(this == 6756L)
    }

    part2(testInput).apply {
        this.println()
        check(this == 467835L)
    }

    val input = readInput(dayId)
    "running with input data".println()
    """
        Results:
            part1: ${part1(input)}
            part 2: ${part2(input)}
    """.trimIndent().println()

}
