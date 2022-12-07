import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("input.txt")!!
        .readText()
        .lines()

    for (line in text) {
        if (line.isEmpty()) break
        
        startMarkerFinder(line, 4, "packet") //6a
        startMarkerFinder(line, 14, "message") //6b
        logger.debug("")
    }
}

private fun startMarkerFinder(line: String, markerLength: Int, markerType: String) {
    for (idx in 0..line.length - markerLength) {
        val sequence = line.toList().subList(idx, idx + markerLength)
        logger.debug(sequence.toString())
        if (sequence.distinct().size == markerLength) {
            logger.info("start-of-$markerType marker: ${idx + markerLength}")
            break
        }
    }
}
