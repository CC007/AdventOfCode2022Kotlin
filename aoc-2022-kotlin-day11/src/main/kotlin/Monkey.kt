class Monkey(val items: MutableList<Int>, private val operation: (Int) -> Int, private val test: (Int) -> Int, val modulo: Int) {
    var inspectionCount = 0

    fun inspect(doDivide: Boolean) {
        if (doDivide) inspect11a() else inspect11b()
    }

    fun inspect11a() {
        inspectionCount += items.size
        items.logTrace("Old items:")
        val beforeDivision = items.map(operation)
        beforeDivision.logTrace("New items before division:")
        val newItems = beforeDivision.map { it / 3 }
        newItems.logTrace("New items:")
        items.clear()
        items.addAll(newItems)
    }

    fun inspect11b() {
        inspectionCount += items.size
        items.logTrace("Old items:")
        val newItems = items.map(operation)
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