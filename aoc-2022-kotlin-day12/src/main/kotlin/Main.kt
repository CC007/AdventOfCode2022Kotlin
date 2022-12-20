import Square.Grid

fun main() {
    val text = object {}.javaClass
        .getResource("input.txt")!!
        .readText()
        .lines()

    "Parsing file".logDebug()
    val grid = parseFile(text)
    grid.toString()::formatPretty[2, 0]//.logDebug()

    val shortestPaths: MutableMap<Square, Int> = mutableMapOf()
    grid.forEach { shortestPaths.putAll(it.map { it to Int.MAX_VALUE }) }
    shortestPaths.toString()::formatPretty[2, 0]//.logTrace()

    "Calculating shortest paths".logDebug()
    var searchSpace: MutableList<Pair<Square, Int>> = arrayListOf(grid.end to 0)
    do {
        val (square, steps) = searchSpace.removeFirst()
        square.toString()::formatPretty[2, 0].logTrace("Current:");
        { grid.joinToString("\n") {
            it.map { shortestPaths[it] }.joinToString(" ") { if (it == Int.MAX_VALUE) "inf" else "%3d".format(it) }
        }.let { "Steps: $steps, grid:\n$it" } }.logTrace()
        if (steps < shortestPaths[square]!!) shortestPaths[square] = steps
        if (square.left?.canMoveRight == true && shortestPaths[square.left]!! == Int.MAX_VALUE) searchSpace.add(square.left!! to (steps + 1))
        if (square.right?.canMoveLeft == true && shortestPaths[square.right]!! == Int.MAX_VALUE) searchSpace.add(square.right!! to (steps + 1))
        if (square.up?.canMoveDown == true && shortestPaths[square.up]!! == Int.MAX_VALUE) searchSpace.add(square.up!! to (steps + 1))
        if (square.down?.canMoveUp == true && shortestPaths[square.down]!! == Int.MAX_VALUE) searchSpace.add(square.down!! to (steps + 1))
        searchSpace = searchSpace.distinctBy { it.first }.toMutableList()
    } while (square != grid.start)
    { grid.joinToString("\n") {
        it.map { shortestPaths[it] }.joinToString(" ") { if (it == Int.MAX_VALUE) "inf" else "%3d".format(it) }
    }.let { "Grid:\n$it" } }.logDebug()

    //12a
    "Shortest path to end: ${shortestPaths[grid.start]}".logInfo()
    
    //12b
    "Shortest path to end from any a (pos ${shortestPaths.filter { it.key.elevation == 0 }.minBy { it.value }.let { "${it.key.pos}): ${it.value}"}}".logInfo()
}


private fun parseFile(text: List<String>): Grid {
    val charGrid = arrayListOf<List<Char>>()
    for (line in text) {
        if (line.isEmpty()) continue
        charGrid.add(line.toList())
    }
    return Grid(charGrid)
}

