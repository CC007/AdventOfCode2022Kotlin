import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .lines()

    for (line in text) {
        if (line.isEmpty()) break
        
        for (idx in 0 until line.length-3) {
            val sequence = line.toList().subList(idx, idx + 4)
            logger.debug(sequence.toString())
            if (sequence.distinct().size == 4) {
                logger.info("start-of-packet marker: ${idx+4}")
                break
            }
        }
        logger.debug("")
    }
}
