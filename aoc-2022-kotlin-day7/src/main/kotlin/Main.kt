import mu.KotlinLogging
import java.util.function.Consumer
import java.util.function.Predicate

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .lines()

    val parsed = Parser(text).parseInput()
    logger.debug(parsed.toString())
    visit(parsed, { it is Dir }) {
        logger.info("${it.name}: ${it.getTotalSize()}")
    }

    var totalSizeUnder100kSum = 0
    visit(parsed, { it is Dir && it.getTotalSize() < 100_000 }) {
        totalSizeUnder100kSum += it.getTotalSize()
    }
    logger.info("Total size of dirs < 100k: $totalSizeUnder100kSum")
}

fun visit(path: Path, criteria: Predicate<Path>, action: Consumer<Path>) {
    if (criteria.test(path)) {
        action.accept(path)
    }
    if (path is Dir) {
        path.values.forEach { visit(it, criteria, action) }
    }
}