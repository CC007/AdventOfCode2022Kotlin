import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .split("\n")

    var fullyContainedCount = 0
    for (line in text) {
        if (line.isEmpty()) break
        val sectionAssignments = line.split(",")
        val sectionRanges = sectionAssignments.map { it.split("-").map(String::toInt) }
        logger.debug(sectionRanges.toString())
        val aContainsB = doesAContainB(sectionRanges[0], sectionRanges[1])
        val bContainsA = doesAContainB(sectionRanges[1], sectionRanges[0])
        logger.debug(aContainsB.toString())
        logger.debug(bContainsA.toString())
        if (aContainsB || bContainsA) {
            fullyContainedCount++
        }
    }

    logger.info("Fully contained count: $fullyContainedCount")
}

private fun doesAContainB(a: List<Int>, b: List<Int>): Boolean = a[0] <= b[0] && a[1] >= b[1]