import utils.*

fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .lines()

    "Parsing file".logDebug()
    val packetPairs = parseFile(text).logDebug("Packet pairs:")

    packetPairs.map { (left, right) -> left < right }
        .mapIndexed { index, rightOrder -> index + 1 to rightOrder }
        .logDebug("Correct packet order:")
        .filter { (_, rightOrder) -> rightOrder }
        .sumOf { (index, _) -> index }
        .logInfo()
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
