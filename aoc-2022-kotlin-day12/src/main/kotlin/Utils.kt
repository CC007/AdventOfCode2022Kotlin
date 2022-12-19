import mu.KotlinLogging
import kotlin.math.max

inline fun <T> T.logTrace(prefix: String = ""): T {
    val logger = KotlinLogging.logger {}
    if (prefix.isEmpty()) {
        logger.trace(this.toString())
    } else logger.trace("$prefix $this")
    return this
}

inline fun <T> T.logDebug(prefix: String = ""): T {
    val logger = KotlinLogging.logger {}
    if (prefix.isEmpty()) logger.debug(this.toString())
    else logger.debug("$prefix $this")
    return this
}

inline fun <T> T.logInfo(prefix: String = ""): T {
    val logger = KotlinLogging.logger {}
    if (prefix.isEmpty()) logger.info(this.toString())
    else logger.info("$prefix $this")
    return this
}

fun String.formatPretty(indentSize: Int = 2, currentIndent: Int = 0): String {
    val nextIndent = " ".repeat(currentIndent + indentSize)
    val indent = " ".repeat(currentIndent)
    val prevIndent = " ".repeat(max(currentIndent - indentSize, 0))

    val curlyBracesOpenRegex = "([^\\[\\]{},]*)\\{(.*)".toRegex()
    val curlyBracesCloseRegex = "([^\\[\\]{},]*)}(.*)".toRegex()
    val squareBracesOpenRegex = "([^\\[\\]{},]*)\\[(.*)".toRegex()
    val squareBracesCloseRegex = "([^\\[\\]{},]*)](.*)".toRegex()
    val commaRegex = "([^\\[\\]{},]*),(.*)".toRegex()

    val curlyBracesOpenMatch = curlyBracesOpenRegex.matchEntire(this)
    val curlyBracesCloseMatch = curlyBracesCloseRegex.matchEntire(this)
    val squareBracesOpenMatch = squareBracesOpenRegex.matchEntire(this)
    val squareBracesCloseMatch = squareBracesCloseRegex.matchEntire(this)
    val commaMatch = commaRegex.matchEntire(this)

    return (if (currentIndent == 0) "\n" else "") + when {
        curlyBracesOpenMatch is MatchResult -> curlyBracesOpenMatch.groupValues[1] + "{" + "\n$nextIndent" +
                curlyBracesOpenMatch.groupValues[2].formatPretty(indentSize, currentIndent + indentSize).trim(' ')

        curlyBracesCloseMatch is MatchResult -> curlyBracesCloseMatch.groupValues[1] + "\n$prevIndent" + "}" +
                curlyBracesCloseMatch.groupValues[2].formatPretty(indentSize, currentIndent - indentSize).trim(' ')

        squareBracesOpenMatch is MatchResult -> squareBracesOpenMatch.groupValues[1] + "[" + "\n$nextIndent" +
                squareBracesOpenMatch.groupValues[2].formatPretty(indentSize, currentIndent + indentSize).trim(' ')

        squareBracesCloseMatch is MatchResult -> squareBracesCloseMatch.groupValues[1] + "\n$prevIndent" + "]" +
                squareBracesCloseMatch.groupValues[2].formatPretty(indentSize, currentIndent - indentSize).trim(' ')

        commaMatch is MatchResult -> commaMatch.groupValues[1] + "," + "\n$indent" +
                commaMatch.groupValues[2].formatPretty(indentSize, currentIndent).trim(' ')

        else -> this
    }
}