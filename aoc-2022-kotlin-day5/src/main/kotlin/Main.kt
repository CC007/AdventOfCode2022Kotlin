import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .lines()

    var suppliesSection = true
    val supplies = arrayListOf<ArrayDeque<Char>>()
    for (line in text) {
        if (suppliesSection) {
            if (line.isEmpty()) {
                suppliesSection = false
                logger.debug(supplies.toString())
                continue
            }
            registerCrates(line, supplies)
        } else {
            if (line.isEmpty()) break

            rearrangeCrates(line, supplies)
        }
    }
    logger.info("Top crates: ${getTopCrates(supplies)}")
}

private fun registerCrates(line: String, supplies: ArrayList<ArrayDeque<Char>>) {
    logger.debug(line)
    if (line.trim().first() != '[') return
    for (charIdx in 1 until line.length step 4) {
        val stackIdx = charIdx / 4
        logger.debug("$stackIdx ($charIdx): ${line[charIdx]}")

        val stack = supplies.getOrElse(stackIdx) { _ -> arrayDequeOf() }
        if (supplies.size <= stackIdx) supplies.add(stack)

        if (line[charIdx - 1] == '[') stack.addFirst(line[charIdx])
    }
}

private fun rearrangeCrates(line: String, supplies: ArrayList<ArrayDeque<Char>>) {
    logger.debug(line)
    val (count, from, to) = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        .find(line)!!
        .groupValues
        .drop(1)
        .onEach { logger.debug(it) }
        .map { it.toInt() }
    logger.debug(supplies.toString())
    for (i in 0 until count) {
        supplies[to - 1].push(supplies[from - 1].pop())
        logger.debug(supplies.toString())
    }
}

private fun getTopCrates(supplies: java.util.ArrayList<ArrayDeque<Char>>): String {
    return supplies.map { it.pop() }.joinToString("")
}

private fun <T> arrayDequeOf(vararg elements: T) = ArrayDeque(elements.toList())
private fun <T> ArrayDeque<T>.push(element: T) = addLast(element)
private fun <T> ArrayDeque<T>.pop() = removeLast()    