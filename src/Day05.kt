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
                            mapped
                        } else {
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

    fun part2(input: List<String>): Long {
        val rangesPairs = input[0].toListOfLong().toMutableList()

        val ranges = (rangesPairs.indices step 2).map {
            val start = rangesPairs[it]
            val length = rangesPairs[it + 1]
            (start until start + length)
        }

        val maps = mutableListOf<List<Line>>()

        var i = 1;
        while (i < input.size) {
            var line = input[i]
            when {
                mapLineRegex.matches(line) -> {
                    println("line $line")
                    val curreMap = mutableListOf<Line>()
                    while (line.isNotEmpty() && i < input.size - 1) {
                        i++
                        line = input[i]
                        if (line.isEmpty()) {
                            break
                        }
                        curreMap.add(parseLine(line))
                    }
                    maps.add(curreMap)
                }
                else -> {
                    i++
                }
            }
        }
        var min = Long.MAX_VALUE
        for (range in ranges) {
            // FIXME: OOM
            var values = range.toList()
            for (map in maps) {
                values = values.map {
                    val mapLine = map.find { rg -> rg.sourceRange.contains(it) }
                    if (mapLine != null) {
                        val mapped = mapLine.destinationStart + mapLine.sourceRange.indexOf(it)
                        mapped
                    } else {
                        it
                    }
                }
            }
            min = min.coerceAtMost(values.min())
        }

        return min
    }


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
