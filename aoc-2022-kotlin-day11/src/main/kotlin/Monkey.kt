class Monkey(val items: MutableList<Long>, private val operation: (Long) -> Long, private val test: (Long) -> Int, val modulo: Int) {
    var inspectionCount = 0

    fun inspect(reliefOperation: (Long) -> Long) {
        inspectionCount += items.size
        items.logTrace("Old items:")
        val beforeDivision = items.map(operation)
        beforeDivision.logTrace("New items before relief:")
        val newItems = beforeDivision.map(reliefOperation)
        newItems.logTrace("New items:")
        items.clear()
        items.addAll(newItems)
    }

    fun test(monkeys: List<Monkey>) {
        while (items.isNotEmpty()) {
            val item = items.removeFirst()
            monkeys[test.invoke(item)].items.add(item)
        }
    }
}