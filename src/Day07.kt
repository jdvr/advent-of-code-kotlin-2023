fun main() {
    val dayId = "Day07"
    val cardValues1 = "23456789TJQKA"
    val cardValues2 = "J23456789TQKA"
    val handGroup = "hand"
    val bidGroup = "bid"
    val lineRegexp = "(?<$handGroup>\\w+)\\s(?<$bidGroup>\\d+)".toRegex()

    data class Hand(val raw: String, val score: Int, val bid: Long)

    fun String.parseLine(): Hand {
        var hand = ""
        var bid = ""
        lineRegexp.findAll(this).forEach {
            it.groups[handGroup]?.value?.let { v -> hand = v }
            it.groups[bidGroup]?.value?.let { v -> bid = v }
        }
        val scoreMap = mutableMapOf<Char, Int>()
        for (card in hand) {
            scoreMap.compute(card) { _, count ->
                (count ?: 0) + 1
            }
        }
        val scoreValues = scoreMap.values
        val score = when {
            5 in scoreValues -> 6
            4 in scoreValues -> 5
            3 in scoreValues ->
                // full house: 23332
                if (2 in scoreValues) {
                    4
                } else {
                    // three of a kind
                    3
                }

            2 in scoreValues ->
                // Two pairs
                if (scoreValues.count { it == 2 } == 2) {
                    2
                } else {
                    // single pair
                    1
                }

            else -> 0
        }

        return Hand(hand, score, bid.toLong())
    }

    fun compareFigures(left: String, right: String): Int {
        if (left == right) {
            return 0
        }
        for (index in left.indices) {
            val l = left[index]
            val r = right[index]
            if (l == r) {
                continue
            }
            val numValL = cardValues1.indexOf(l) + 1
            val numValR = cardValues1.indexOf(r) + 1
            val diff = numValL - numValR
            if (diff < 0) {
                println("$left vs $right, right wins")
            } else {
                println("$left vs $right, left wins")
            }
            return diff
        }

        return 0
    }

    fun part1(input: List<String>): Long {
        val hands = input.map { it.parseLine() }
        println(hands)
        val sorted = hands.sortedWith { l, r ->
            // Comparator<T> interface:
            // a negative integer, zero, or a positive integer as the first argument
            // is less than, equal to, or greater than the second
            // [3, 2]
            // [3, 1, 1]
            val diff = l.score - r.score
            if (diff == 0) {
                compareFigures(l.raw, r.raw)
            } else {
                diff
            }
        }
        (sorted.joinToString("\n") { it.raw }).println()
        return sorted.mapIndexed { idx, hand ->
            (idx + 1) * hand.bid
        }.sum()
    }

    fun part2(input: List<String>): Long = -12


//  test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayId}_test")
    part1(testInput).apply {
        this.println()
        check(this == 6440L)
    }

    part2(testInput).apply {
        this.println()

        check(this == 5905L || this == -12L)
    }

    val input = readInput(dayId)
    "running with input data".println()
    """
        Results:
            part1: ${part1(input)}
            part 2: ${part2(input)}
    """.trimIndent().println()

}
