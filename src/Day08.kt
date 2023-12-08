fun main() {
    val dayId = "Day08"
    val nodeGroup = "hand"
    val firstGroup = "first"
    val secondGroup = "second"
    val lineRegexp = "(?<$nodeGroup>\\w+)\\s+=\\s+\\((?<$firstGroup>\\w+),\\s(?<$secondGroup>\\w+)\\)".toRegex()


    data class Node(val value: String, val left: String, val right: String) {
        fun move(direction: Char): String = if (direction == 'L') {
            left
        } else {
            right
        }

        fun isEnd(): Boolean = value == "ZZZ"
    }

    fun String.parseLine(): Node {
        val groups = lineRegexp.find(this)?.groups!!
        val value = groups[nodeGroup]!!.value
        val left = groups[firstGroup]!!.value
        val right = groups[secondGroup]!!.value

        return Node(value, left, right)
    }

    fun part1(input: List<String>): Long {
        val lines = input.toMutableList()
        val directions = lines.removeFirst()
        val nodeIndex = lines.filter { it.isNotEmpty() }
            .map { it.parseLine() }
            .groupBy({ it.value }) { it }
        val maxDir = directions.length

        var currNode = nodeIndex["AAA"]!!.first()
        var steps = 0L
        while (!currNode.isEnd()) {
            val dir = directions[(steps % maxDir).toInt()]
            val nextNodeVal = currNode.move(dir)
            currNode = nodeIndex[nextNodeVal]!!.first()
            steps++
        }
        
        return steps
    }

    fun part2(input: List<String>): Long = -12


//  test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayId}_test")
    part1(testInput).apply {
        this.println()
        check(this == 6L)
    }

    part2(testInput).apply {
        this.println()

        check(this == 6839L || this == -12L)
    }

    val input = readInput(dayId)
    "running with input data".println()
    """
        Results:
            part1: ${part1(input)}
            part 2: ${part2(input)}
    """.trimIndent().println()

}
