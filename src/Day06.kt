import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val dayId = "Day06"

    /*
    maxT = 7
    d = 9
    x = 2
    moved = 1 * (7  - 1) = 6
    moved = 2 * (7  - 2) = 10
    moved = x * (maxT  - x)
    x * (maxT  - x) - moved = 0
    -x2 + maxT*x - moved = 0
    // segundo grado
    x = (-b +- sqrt(b2 - 4ac) / 2a
    a = -1
    b = maxT
    c = -moved = d -> min distance
    x = (b +- sqrt(b2 + -4c) / -2
     */
    fun findPossibilities(maxTime: Long, distance: Long): Long {
        val b = maxTime.toDouble()
        val c = distance * -1.0 // to have -c (moved from the other side of "=")
        val x = (b - sqrt(b.pow(2) + 4 * c)) / 2
        // This is the min number of loss
        var loosingCount = floor(x).toLong()
        // double the range
        loosingCount *= 2
        // Count x = 0
        loosingCount += 1
        return maxTime - loosingCount
}

    fun part1(input: List<String>): Long {
        val times = input[0].toListOfLong()
        val distances = input[1].toListOfLong()

        return times.mapIndexed{ idx, maxTime ->
            val distance = distances[idx]
            findPossibilities(maxTime, distance)
        }.filter { it != 0L }.reduce { acc, l -> acc * l }
    }

    fun part2(input: List<String>): Long {
        val maxTime = input[0].filter { it.isDigit() }.toLong()
        val distance = input[1].filter { it.isDigit() }.toLong()

        return findPossibilities(maxTime, distance)
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
