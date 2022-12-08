import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("input.txt")!!
        .readText()
        .lines()

    val trees = arrayListOf<List<Int>>()
    for (line in text) {
        if (line.isEmpty()) break

        trees.add(line.toList().map { it.digitToInt() })
    }
    logger.debug(trees.toString())
    logger.info("Total trees visible: ${calculateTotalVisible(trees)}")
    logger.debug("")
    logger.info("Highest scenic score: ${calculateHighestScenicScore(trees)}")


}

//8a
private fun calculateTotalVisible(trees: List<List<Int>>): Int {
    // Start with outer visible trees
    var totalVisible = 2 * trees.size + 2 * trees[0].size - 4

    // Calculate how many trees are visible in the inner rings
    traverseInnerGrid(trees, { tree: Int, left: List<Int>, right: List<Int>, top: List<Int>, bottom: List<Int> ->
        tree > left.max() || tree > right.max() || tree > top.max() || tree > bottom.max()
    }, { resultRow: List<Boolean> ->
        logger.debug(resultRow.joinToString(" ") { it.toString()[0].uppercase() })
        totalVisible += resultRow.count { it }
    })
    return totalVisible
}

//8b
private fun calculateHighestScenicScore(trees: List<List<Int>>): Int {
    val scenicScores: MutableList<List<Int>> = arrayListOf()

    // Calculate the scenic scores for trees in the inner rings 
    // Outer ring trees are always multiplied by 0, so skip those
    traverseInnerGrid(trees, { tree: Int, left: List<Int>, right: List<Int>, top: List<Int>, bottom: List<Int> ->
        logger.trace("")
        score(tree, left.reversed()) * score(tree, right) * score(tree, top.reversed()) * score(tree, bottom)
    }, { resultRow: List<Int> ->
        logger.debug(resultRow.joinToString(" "))
        scenicScores.add(resultRow)
    })
    return scenicScores.maxOf { it.max() }
}

// Traverse the plot, except for the outer ring
private fun <T> traverseInnerGrid(
    trees: List<List<Int>>,
    treeAction: (Int, List<Int>, List<Int>, List<Int>, List<Int>) -> T,
    rowAction: (List<T>) -> Unit,
) {
    for (rowIdx in 1 until trees.size - 1) {
        val resultRow: MutableList<T> = arrayListOf()
        for (colIdx in 1 until trees.size - 1) {
            val tree = trees[rowIdx][colIdx]

            val row = trees.row(rowIdx)
            val col = trees.col(colIdx)

            val left = row.subList(0, colIdx)
            val right = row.subList(colIdx + 1, row.size)
            val top = col.subList(0, rowIdx)
            val bottom = col.subList(rowIdx + 1, col.size)

            val result = treeAction.invoke(tree, left, right, top, bottom)
            resultRow.add(result)
        }
        rowAction.invoke(resultRow)
    }
}

private fun score(tree: Int, otherTrees: List<Int>): Int {
    logger.trace("Tree: $tree, other trees: $otherTrees")
    var score = 0
    while (score < otherTrees.size) {
        val otherTree = otherTrees[score]
        score++
        if (tree <= otherTree) break
    }
    logger.trace("Score: $score")
    return score
}

private fun <T> List<List<T>>.col(idx: Int) = this.map { it[idx] }
private fun <T> List<List<T>>.row(idx: Int) = this[idx]
