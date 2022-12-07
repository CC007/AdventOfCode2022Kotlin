import mu.KotlinLogging
import java.util.function.Consumer
import java.util.function.Predicate

private val logger = KotlinLogging.logger {}


fun main() {
    val text = object {}.javaClass
        .getResource("input.txt")!!
        .readText()
        .lines()

    val parsed = Parser(text).parseInput()
    logger.debug(parsed.toString())
    visit(parsed, { it is Dir }) {
        logger.debug("${it.name}: ${it.getTotalSize()}")
    }

    //7a
    var totalSizeUnder100kSum = 0
    visit(parsed, { it is Dir && it.getTotalSize() < 100_000 }) {
        totalSizeUnder100kSum += it.getTotalSize()
    }
    logger.info("Total size of dirs < 100k: $totalSizeUnder100kSum")
    
    //7b
    val unused = 70_000_000 - parsed.getTotalSize()
    logger.debug("Unused: $unused")
    val sizeToFree = 30_000_000 - unused
    logger.debug("Amount of space that needs to be freed up: $sizeToFree")
    
    val availableDirsToFree = arrayListOf<Path>()
    visit(parsed, { it is Dir && it.getTotalSize() > sizeToFree }) {
        availableDirsToFree.add(it)
    }
    val smallestDirToFree = availableDirsToFree.minBy { it.getTotalSize() }
    logger.info("Smallest dir to delete to free enough space: ${smallestDirToFree.name} (size: ${smallestDirToFree.getTotalSize()})")
}

fun visit(path: Path, criteria: Predicate<Path>, action: Consumer<Path>) {
    if (criteria.test(path)) {
        action.accept(path)
    }
    if (path is Dir) {
        path.values.forEach { visit(it, criteria, action) }
    }
}