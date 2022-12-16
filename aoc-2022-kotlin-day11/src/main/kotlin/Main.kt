import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .lines()

    var lineCounter = 1
    val monkeys = arrayListOf<Monkey>()
    while (lineCounter < text.size) {
        text.component5()
        val (startingItemsStr, operationStr, testStr, trueStr, falseStr) = text.drop(lineCounter)
        val startingItems = startingItemsStr.substring(18).split(", ").map(String::toInt).logDebug()
        val operation = getOperation(operationStr)
        val test = getTest(testStr, trueStr, falseStr)
        monkeys.add(Monkey(startingItems, operation, test))
        lineCounter += 7
    }
}

private fun getOperation(operationStr: String): (Int) -> Int {
    val operationRegex = " {2}Operation: new = (?<left>\\w+) (?<operator>.) (?<right>\\w+)".toRegex()
    val operationRegexGroups = operationRegex.matchEntire(operationStr)!!.groups
    "${operationRegexGroups["left"]!!.value} ${operationRegexGroups["operator"]!!.value} ${operationRegexGroups["right"]!!.value}".logDebug()
    val operation: (Int) -> Int = { old: Int ->
        performOperation(
            old,
            operationRegexGroups["left"]!!.value,
            operationRegexGroups["operator"]!!.value,
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
    when (operator) {
        "*" -> parseValue(left, old) * parseValue(right, old)
        "+" -> parseValue(left, old) + parseValue(right, old)
    }
    return 1
}

fun parseValue(toParse: String, old: Int): Int {
    if (toParse == "old") return old
    return toParse.toInt()
}

private inline fun <T> T.logDebug(prefix: String = ""): T {
    if (prefix.isEmpty()) logger.debug(this.toString())
    else logger.debug("$prefix $this")
    return this
}

private inline fun <T> T.logInfo(prefix: String = ""): T {
    logger.info("$prefix$this")
    return this
}