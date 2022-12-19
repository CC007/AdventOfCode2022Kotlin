import Square.Grid

fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .lines()

    val grid = parseFile(text)
    grid.toString().formatPretty().logDebug()

    val shortestPaths: MutableMap<Square, Int> = mutableMapOf<Square, Int>()
    grid.forEach { shortestPaths.putAll(it.map { it to Int.MAX_VALUE }) }
    shortestPaths.toString().formatPretty().logTrace()

    val searchSpace: MutableList<Pair<Square, Int>> = arrayListOf(grid.start to 0)

    do {
        val (square, steps) = searchSpace.removeFirst()
        square.toString().formatPretty().logTrace("Current:")
        grid.joinToString("\n") {
            it.map { shortestPaths[it] }.joinToString(" ") { if (it == Int.MAX_VALUE) "inf" else "%3d".format(it) }
        }.let { "Steps: $steps, grid:\n$it" }.logTrace()
        if (steps < shortestPaths[square]!!) shortestPaths[square] = steps
        if (square.canMoveLeft && shortestPaths[square.left]!! == Int.MAX_VALUE) searchSpace.add(square.left!! to (steps + 1))
        if (square.canMoveRight && shortestPaths[square.right]!! == Int.MAX_VALUE) searchSpace.add(square.right!! to (steps + 1))
        if (square.canMoveUp && shortestPaths[square.up]!! == Int.MAX_VALUE) searchSpace.add(square.up!! to (steps + 1))
        if (square.canMoveDown && shortestPaths[square.down]!! == Int.MAX_VALUE) searchSpace.add(square.down!! to (steps + 1))
    } while (square != grid.end)
    
    "Shortest path to end: ${shortestPaths[grid.end]}".logInfo()
}


private fun parseFile(text: List<String>): Grid {
    val charGrid = arrayListOf<List<Char>>()
    for (line in text) {
        if (line.isEmpty()) continue
        charGrid.add(line.toList())
    }
    return Grid(charGrid)
}

