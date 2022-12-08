import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .lines()

    val trees = arrayListOf<List<Int>>()
    for (line in text) {
        if (line.isEmpty()) break

        trees.add(line.toList().map { it.digitToInt() })
    }
    logger.debug(trees.toString())

    // Start with outer visible trees
    var totalVisible = 2 * trees.size + 2 * trees[0].size - 4
    
    // Calculate how many trees are visible in the inner rings
    for (rowIdx in 1 until trees.size - 1) {
        val result: MutableList<Boolean> = arrayListOf()
        for (colIdx in 1 until trees.size - 1) {
            val cell = trees[rowIdx][colIdx]
            val row = trees.row(rowIdx)
            val col = trees.col(colIdx)
            val left = row.subList(0, colIdx)
            val right = row.subList(colIdx + 1, row.size)
            val top = col.subList(0, rowIdx)
            val bottom = col.subList(rowIdx + 1, col.size)
            val visible = cell > left.max() || cell > right.max() || cell > top.max() || cell > bottom.max()
            result.add(visible)
            totalVisible += if (visible) 1 else 0
        }
        logger.debug(result.joinToString(" ") { it.toString()[0].uppercase() })
    }
    logger.info("Total trees visible: $totalVisible")
}

private fun <T> List<List<T>>.col(idx: Int) = this.map { it[idx] }
private fun <T> List<List<T>>.row(idx: Int) = this[idx]