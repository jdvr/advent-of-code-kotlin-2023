fun main() {
    val dayId = "Day07"
    val cardValues = "23456789TJQKA"
    val handGroup = "hand"
    val bidGroup = "bid"
    val lineRegexp = "(?<$handGroup>\\w+)\\s(?<$bidGroup>\\d+)".toRegex()

    data class Hand(val raw: String, val score: Int, val bid: Long)

    fun String.parseLine(useJoker: Boolean = false): Hand {
        var hand = ""
        var bid = ""
        lineRegexp.findAll(this).forEach {
            it.groups[handGroup]?.value?.let { v -> hand = v }
            it.groups[bidGroup]?.value?.let { v -> bid = v }
        }
        val scoreMap = mutableMapOf<Char, Int>()
        var jokers = 0

        for (card in hand) {
            if (card == 'J') {
                jokers++
            }
            scoreMap.compute(card) { _, count ->
                (count ?: 0) + 1
            }
        }
        val scoreValues = scoreMap.values
        var score = when {
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
        score = if (useJoker && jokers != 0) {
            when (score) {
                // JJJJ2 four of a kind -> five of a kind 5 -> 6
                5 -> 6
                // 2JJJ2 -> becomes 6
                // J333J -> becomes 6
                // JJJ33 -> becomes 6
                // 22J67 single pair (1) -> becomes 3 of a kind
                // T55J5 -> becomes four of a kind 3 -> 5
                4, 3, 1 -> score + 2
                // 33JJ1 -> becomes four of a kind 2 -> 5
                // 33J11 -> becomes full 2 -> 4
                2 -> if (jokers == 1) {
                    4
                } else {
                    5
                }
                // J1234 -> becomes one pair
                0 -> 1
                // 33J41 -> becomes three of a kind 3
                else -> score
            }
        } else {
            score
        }

        return Hand(hand, score, bid.toLong())
    }

    fun cardStretch(card: Char, useJoker: Boolean): Int = when (useJoker && card == 'J') {
        true -> 1
        false -> cardValues.indexOf(card) + 2
    }

    fun compareFigures(left: String, right: String, useJoker: Boolean = false): Int {
        if (left == right) {
            return 0
        }

        for (index in left.indices) {
            val l = left[index]
            val r = right[index]
            if (l == r) {
                continue
            }
            val numValL = cardStretch(l, useJoker)
            val numValR = cardStretch(r, useJoker)
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
        val sorted = hands.sortedWith { l, r ->
            // Comparator<T> interface:
            // a negative integer, zero, or a positive integer as the first argument
            // is less than, equal to, or greater than the second

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

    fun part2(input: List<String>): Long {
        val hands = input.map { it.parseLine(useJoker = true) }
        val sorted = hands.sortedWith { l, r ->
            // Comparator<T> interface:
            // a negative integer, zero, or a positive integer as the first argument
            // is less than, equal to, or greater than the second
            val diff = l.score - r.score
            if (diff == 0) {
                compareFigures(l.raw, r.raw, useJoker = true)
            } else {
                diff
            }
        }
        (sorted.joinToString("\n") { "${it.raw} ${it.score}" }).println()
        return sorted.mapIndexed { idx, hand ->
            (idx + 1) * hand.bid
        }.sum()
    }


//  test if implementation meets criteria from the description, like:
    val testInput = readInput("${dayId}_test")
    part1(testInput).apply {
        this.println()
        check(this == 6592L)
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
