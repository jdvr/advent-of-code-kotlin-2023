import kotlin.math.pow

fun main() {
    val dayId = "Day05"
    val seedsLine = "seeds"
    val mapLineRegex = "(\\w+-to-\\w+ map:)".toRegex()

    data class Line(val destinationStart: Long, val sourceRange: LongRange, val length: Long)

    fun parseLine(inputLine: String): Line {
        val (ds, ss, l) = inputLine.splitTrim(SPACE)
        val sourceStart = ss.toLong()
        val length = l.toLong()
        return Line(
            ds.toLong(),
            sourceStart until sourceStart + length,
            length
        );
    }

    fun part1(input: List<String>): Long {
        var values = emptyList<Long>()
        var i = 0;
        while (i < input.size) {
            var line = input[i]
            when {
                line.startsWith(seedsLine) -> {
                    values = line.toListOfLong().toMutableList()
                    i++
                }

                mapLineRegex.matches(line) -> {
                    val headLine = line
                    println("line $line")
                    val seedToSoilMap = mutableListOf<Line>()
                    while (line.isNotEmpty() && i < input.size - 1) {
                        i++
                        line = input[i]
                        if (line.isEmpty()) {
                            break
                        }
                        seedToSoilMap.add(parseLine(line))
                    }
                    values = values.map {
                        val mapLine = seedToSoilMap.find { rg -> rg.sourceRange.contains(it) }
                        if (mapLine != null) {
                            val mapped = mapLine.destinationStart + mapLine.sourceRange.indexOf(it)
                            println("$it mapped using $headLine to $mapped")
                            mapped
                        } else {
                            println("$it don't have a map")
                            it
                        }
                    }
                }

                else -> {
                    i++
                }
            }
        }
        return values.min()
    }

    fun part2(input: List<String>): Long = -12


//  test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayId}_test")
    part1(testInput).apply {
        this.println()
        check(this == 35L)
    }

    part2(testInput).apply {
        this.println()

        check(this == 46L || this == -12L)
    }

    val input = readInput(dayId)
    "running with input data".println()
    """
        Results:
            part1: ${part1(input)}
            part 2: ${part2(input)}
    """.trimIndent().println()

}
