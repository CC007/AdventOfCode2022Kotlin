import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("input.txt")!!
        .readText()
        .lines()

    var suppliesSection = true
    val supplies9000 = arrayListOf<ArrayDeque<Char>>() //5a
    val supplies9001 = arrayListOf<ArrayDeque<Char>>() //5b
    for (line in text) {
        if (suppliesSection) {
            if (line.isEmpty()) {
                suppliesSection = false
                logger.debug(supplies9000.toString())
                continue
            }
            registerCrates(line, supplies9000)
            registerCrates(line, supplies9001)
        } else {
            if (line.isEmpty()) break

            moveCratesWithCrateMover9000(line, supplies9000)
            moveCratesWithCrateMover9001(line, supplies9001)
        }
    }
    logger.info("Top crates with CrateMover9000: ${getTopCrates(supplies9000)}")
    logger.info("Top crates with CrateMover9001: ${getTopCrates(supplies9001)}")
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

private fun moveCratesWithCrateMover9000(line: String, supplies: ArrayList<ArrayDeque<Char>>) {
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

private fun moveCratesWithCrateMover9001(line: String, supplies: ArrayList<ArrayDeque<Char>>) {
    logger.debug(line)
    val (count, from, to) = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        .find(line)!!
        .groupValues
        .drop(1)
        .onEach { logger.debug(it) }
        .map { it.toInt() }
    logger.debug(supplies.toString())
    val tmp = arrayDequeOf<Char>()
    for (i in 0 until count) {
        tmp.push(supplies[from - 1].pop())
        logger.debug("$supplies with $tmp")
    }
    for (i in 0 until count) {
        supplies[to - 1].push(tmp.pop())
        logger.debug("$supplies with $tmp")
    }
}

private fun getTopCrates(supplies: java.util.ArrayList<ArrayDeque<Char>>): String {
    return supplies.map { it.pop() }.joinToString("")
}

private fun <T> arrayDequeOf(vararg elements: T) = ArrayDeque(elements.toList())
private fun <T> ArrayDeque<T>.push(element: T) = addLast(element)
private fun <T> ArrayDeque<T>.pop() = removeLast()    