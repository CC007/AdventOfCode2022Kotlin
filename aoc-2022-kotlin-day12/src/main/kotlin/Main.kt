import mu.KotlinLogging
import Square.Grid

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .lines()

    val grid = parseFile(text)//.logDebug()
    grid.toString().formatPretty().logDebug()
//    "Hello{World{!}}".formatPretty().logDebug()
    val grid2 = grid.start.grid
}


private fun parseFile(text: List<String>): Grid {
    val charGrid = arrayListOf<List<Char>>()
    for (line in text) {
        if (line.isEmpty()) continue
        charGrid.add(line.toList())
    }
    return Grid(charGrid)
}

