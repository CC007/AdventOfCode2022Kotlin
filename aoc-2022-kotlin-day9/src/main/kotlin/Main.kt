import mu.KotlinLogging
import kotlin.math.abs
import kotlin.math.sign

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("inputExample2.txt")!!
        .readText()
        .lines()

    val tailHistory = hashSetOf<Pair<Int, Int>>()
    val longTailHistory = hashSetOf<Pair<Int, Int>>()
    var head = 0 to 0
    var tail = 0 to 0
    val oneToNine = (1..9).map { 0 to 0 }.toMutableList() 
    tailHistory.add(tail)
    for (line in text) {
        if (line.isEmpty()) break

        val (direction, distance) = line.split(" ")
            .let { Direction.valueOf(it[0]) to it[1].toInt() }
        logger.debug("Move $distance in direction $direction")

        (0 until distance).forEach { _ ->
            head = getNewHead(head, direction)

            //9a
            tail = getNewTail(tail, head)
            logger.debug("Head $head, tail: $tail")
            tailHistory.add(tail)

            //9b
            oneToNine[0] = getNewTail(oneToNine[0], head)
            for (i in 1 until 9) {
                oneToNine[i] = getNewTail(oneToNine[i], oneToNine[i-1])
            }
            logger.debug("Head $head, tail: $oneToNine")
            longTailHistory.add(oneToNine[8])
        }
    }
    logGrid(tailHistory)
    logGrid(longTailHistory)
    logger.info("Total positions visited by tail: ${tailHistory.count()}")
    logger.info("Total positions visited by long tail: ${longTailHistory.count()}")
}

fun getNewHead(head: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
    return when (direction) {
        Direction.L -> head.first to head.second - 1
        Direction.R -> head.first to head.second + 1
        Direction.D -> head.first - 1 to head.second
        Direction.U -> head.first + 1 to head.second
    }
}

fun getNewTail(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {
    val difference = head - tail
    if (difference.map { abs(it) }.none { it > 1 }) return tail
    return tail.first + difference.first.sign to tail.second + difference.second.sign
}

private fun logGrid(tailHistory: HashSet<Pair<Int, Int>>) {
    val gridMax = tailHistory.maxOf { it.first } to tailHistory.maxOf { it.second }
    val gridMin = tailHistory.minOf { it.first } to tailHistory.minOf { it.second }
    logger.debug("Grid size: ${gridMax-gridMin} ($gridMin to $gridMax)")
    for (i in gridMax.first downTo gridMin.first) {
        val row = arrayListOf<Char>()
        for (j in gridMin.second..gridMax.second) {
            if (tailHistory.contains(i to j)) row.add('#')
            else row.add('.')
        }
        logger.debug(row.joinToString(""))
    }
}


private operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    return first - other.first to second - other.second
}

private inline fun <T, R> Pair<T, T>.map(transform: (T) -> R): Pair<R, R> {
    return this.toList().map(transform).let { it[0] to it[1] }
}

private inline fun <T> Pair<T, T>.none(predicate: (T) -> Boolean): Boolean {
    return this.toList().none(predicate)
}