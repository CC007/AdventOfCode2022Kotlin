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
    val crt = arrayListOf<MutableList<Boolean>>()
    var row = arrayListOf<Boolean>()
    while (lineCounter < text.size) {
        val line = text[lineCounter]
        if (line.isEmpty()) break

        //10a
        if (cycle % 40 == 20) {
            val signalStrength = cycle * x
            logger.debug("Signal strenght for cycle $cycle: $signalStrength ($cycle * $x)")
            signalStrengthSum += signalStrength
        }

        //10b
        val rowDrawPos = (cycle - 1) % 40
        if (rowDrawPos == 0) {
            row = arrayListOf()
            crt.add(row)
        }
        val isSprite = getIsSprite(rowDrawPos, x)
        row.add(isSprite)
        logger.trace("Sprite position: ${".".repeat(40).mapIndexed {idx, _ -> getChar(getIsSprite(idx, x)) }.joinToString("")}")
        logger.trace("Current CRT row: ${row.map(::getChar).joinToString("")}")
        
        when {
            line.startsWith("noop") -> lineCounter++.also { logger.debug("Execute noop") }
            line.startsWith("addx") && !busy -> busy = true.also { logger.debug("Begin executing addx") }
            line.startsWith("addx") && busy -> {
                busy = false
                val change = line.substring(5).toInt()
                logger.trace("Done adding $change to $x")
                x += change
                lineCounter++
            }
        }

        cycle++
    }
    logger.info("Signal strength sum: $signalStrengthSum")
    logger.info("Image: ")
    printCrt(crt)
}

private fun getIsSprite(rowDrawPos: Int, x: Int) = rowDrawPos in x - 1..x + 1

private fun printCrt(crt: ArrayList<MutableList<Boolean>>) {
    for (row in crt) {
        logger.info(row.map(::getChar).joinToString(""))
    }
}

private fun getChar(it: Boolean) = if (it) '#' else '.'

