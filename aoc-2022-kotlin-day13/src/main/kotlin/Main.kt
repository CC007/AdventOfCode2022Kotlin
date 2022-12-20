import utils.*

fun main() {
    val text = object {}.javaClass
        .getResource("input.txt")!!
        .readText()
        .lines()

    "Parsing file".logDebug()
    val packetPairs = parseFile(text).logDebug("Packet pairs:")

    //13a
    packetPairs.map { (left, right) -> left < right }
        .mapIndexed { index, rightOrder -> index + 1 to rightOrder }
        .logDebug("Correct packet order:")
        .filter { (_, rightOrder) -> rightOrder }
        .sumOf { (index, _) -> index }
        .logInfo("Sum of correctly ordered pair indices:")

    //13b
    val dividerPacket2 = ListValue(arrayListOf(ListValue(arrayListOf(IntegerValue(2)))))
    val dividerPacket6 = ListValue(arrayListOf(ListValue(arrayListOf(IntegerValue(6)))))

    listOf(dividerPacket2, dividerPacket6, *packetPairs.flatMap { it.toList() }.toTypedArray())
        .sorted()
        .also { "Ordered packets:".logDebug() }
        .onEach { it.logDebug() }
        .mapIndexed { index, packet -> index + 1 to packet }
        .filter { (_, packet) -> packet == dividerPacket2 || packet == dividerPacket6 }
        .logDebug("Divider packets:")
        .map { (index, _) -> index }
        .reduce { acc, i -> acc * i }
        .logInfo("Decoder key:")
}


private fun parseFile(text: List<String>): List<Pair<ListValue, ListValue>> {
    val packetPairs = arrayListOf<Pair<ListValue, ListValue>>()
    var idx = 0
    while (idx < text.size) {
        val leftPacketLine = text[idx]
        val rightPacketLine = text[idx + 1]
        val leftPacket = parsePacket(leftPacketLine)
        val rightPacket = parsePacket(rightPacketLine)
        packetPairs.add(leftPacket to rightPacket)
        idx += 3
    }
    return packetPairs
}

fun parsePacket(leftPacketLine: String): ListValue {
    val result = ListValue()

    var remaining = leftPacketLine.substring(1)
    val stack = mutableListOf(result)
    var digits = ""
    while (remaining.isNotEmpty()) {
        when {
            remaining.startsWith('[') -> {
                val newListValue = ListValue()
                stack.peek().add(newListValue)
                stack.push(newListValue)
            }

            remaining.startsWith(']') -> {
                if (digits.isNotEmpty()) stack.peek().add(IntegerValue(digits.toInt()))
                digits = ""
                stack.pop()
            }

            remaining.startsWith(',') -> {
                if (digits.isNotEmpty()) stack.peek().add(IntegerValue(digits.toInt()))
                digits = ""
            }

            else -> digits += remaining.first()
        }
        remaining = remaining.substring(1)
    }
    return result
}
