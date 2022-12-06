import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main() {
    val text = object {}.javaClass
        .getResource("input.txt")!!
        .readText()
        .split("\n")

    var fullyContainedCount = 0
    var overlapCount = 0
    for (line in text) {
        if (line.isEmpty()) break
        val sectionAssignments = line.split(",")
        val sectionRanges = sectionAssignments.map { it.split("-").map(String::toInt) }
        logger.debug(sectionRanges.toString())
        
        //4a
        val aContainsB = doesAContainB(sectionRanges[0], sectionRanges[1])
        val bContainsA = doesAContainB(sectionRanges[1], sectionRanges[0])
        logger.debug(aContainsB.toString())
        logger.debug(bContainsA.toString())
        if (aContainsB || bContainsA) {
            fullyContainedCount++
        }
        
        //4b
        val aOverlapsB = doesAOverlapB(sectionRanges[0], sectionRanges[1])
        logger.debug(aOverlapsB.toString())
        if (aOverlapsB) {
            overlapCount++
        }
    }

    logger.info("Fully contained count: $fullyContainedCount")
    logger.info("Overlap count: $overlapCount")
}

private fun doesAContainB(a: List<Int>, b: List<Int>): Boolean = a[0] <= b[0] && a[1] >= b[1]
private fun doesAOverlapB(a: List<Int>, b: List<Int>): Boolean = a[0] <= b[1] && a[1] >= b[0]