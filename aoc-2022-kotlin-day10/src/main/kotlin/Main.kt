import mu.KotlinLogging

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("inputExample2.txt")!!
        .readText()
        .lines()

    var x = 1
    var cycle = 1
    var lineCounter = 0
    var busy = false
    var signalStrengthSum = 0
    while (lineCounter < text.size) {
        val line = text[lineCounter]
        if (line.isEmpty()) break


        if (cycle % 40 == 20) {
            val signalStrength = cycle * x
            logger.debug("Signal strenght for cycle $cycle: $signalStrength ($cycle * $x)")
            signalStrengthSum += signalStrength
        }

        when {
            line.startsWith("noop") -> lineCounter++
            line.startsWith("addx") && !busy -> busy = true
            line.startsWith("addx") && busy -> {
                busy = false
                val change = line.substring(5).toInt()
                logger.trace("add $change to $x")
                x += change
                lineCounter++
            }
        }
        cycle++
    }
    logger.info("Signal strength sum: $signalStrengthSum")
}