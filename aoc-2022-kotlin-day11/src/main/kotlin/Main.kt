import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .lines()

    doRounds(text, 20, true)
    doRounds(text, 10000, false)
}

private fun doRounds(text: List<String>, rounds: Int, doDivide: Boolean) {
    val monkeys = parseFile(text)
    val moduloProduct = monkeys.map { it.modulo }.fold(1) { a, b -> a * b}.logDebug("Modulo product:")
    for (round in 1..rounds) {
        for (monkey in monkeys) {
            monkey.inspect(doDivide)
            monkey.test(monkeys)
        }
        round.logDebug("After round")
        monkeys.map { it.items.joinToString(", ") }.forEachIndexed { idx, items -> items.logDebug("Monkey $idx:") }
        logger.debug("")
    }
    val inspectionCounts = monkeys.map { it.inspectionCount }
    inspectionCounts.forEachIndexed { idx, items -> items.logDebug("Monkey $idx inspection count:") }
    val sortedInspectionCounts = inspectionCounts.sortedDescending()
    logger.info("Monkey business: ${sortedInspectionCounts[0].toLong() * sortedInspectionCounts[1].toLong()}")
}

private fun parseFile(text: List<String>): ArrayList<Monkey> {
    var lineCounter = 1
    val monkeys = arrayListOf<Monkey>()
    while (lineCounter < text.size) {
        val (startingItemsStr, operationStr, testStr, trueStr, falseStr) = text.drop(lineCounter)
        val startingItems = startingItemsStr.substring(18).split(", ").map(String::toInt).logDebug()
        val operation = getOperation(operationStr, testStr)
        val test = getTest(testStr, trueStr, falseStr)
        val modulo = testStr.substring(21).toInt()
        monkeys.add(Monkey(startingItems.toMutableList(), operation, test, modulo))
        lineCounter += 7
    }
    logger.debug("")
    return monkeys
}

private fun getOperation(operationStr: String, testStr: String): (Int) -> Int {
    val divisibleBy = testStr.substring(21).toInt().logDebug("Divisible by:")
    val operationRegex = " {2}Operation: new = (?<left>\\w+) (?<operator>.) (?<right>\\w+)".toRegex()
    val operationRegexGroups = operationRegex.matchEntire(operationStr)!!.groups
    "${operationRegexGroups["left"]!!.value} ${operationRegexGroups["operator"]!!.value} ${operationRegexGroups["right"]!!.value}".logDebug()
    val operation: (Int) -> Int = { old: Int ->
        performOperation(
            old,
            operationRegexGroups["operator"]!!.value,
            operationRegexGroups["left"]!!.value,
            operationRegexGroups["right"]!!.value
        )
    }
    return operation
}

fun getTest(testStr: String, trueStr: String, falseStr: String): (Int) -> Int {
    val divisibleBy = testStr.substring(21).toInt().logDebug("Divisible by:")
    val ifTrue = trueStr.substring(29).toInt().logDebug("If true:")
    val ifFalse = falseStr.substring(30).toInt().logDebug("If false:")
    return { value -> if (value % divisibleBy == 0) ifTrue else ifFalse }
}

private fun performOperation(old: Int, operator: String, left: String, right: String): Int {
    return when (operator) {
        "*" -> parseValue(left, old) * parseValue(right, old)
        "+" -> parseValue(left, old) + parseValue(right, old)
        else -> 0
    }
}

fun parseValue(toParse: String, old: Int): Int {
    if (toParse == "old") return old
    return toParse.toInt()
}

fun <T> T.logTrace(prefix: String = ""): T {
    if (prefix.isEmpty()) logger.trace(this.toString())
    else logger.trace("$prefix $this")
    return this
}

fun <T> T.logDebug(prefix: String = ""): T {
    if (prefix.isEmpty()) logger.debug(this.toString())
    else logger.debug("$prefix $this")
    return this
}

fun <T> T.logInfo(prefix: String = ""): T {
    if (prefix.isEmpty()) logger.info(this.toString())
    else logger.info("$prefix $this")
    return this
}