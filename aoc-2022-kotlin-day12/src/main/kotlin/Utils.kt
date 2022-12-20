import mu.KotlinLogging
import kotlin.math.max

inline fun <T> T.logTrace(prefix: String = ""): T {
    val logger = KotlinLogging.logger {}
    this.log(prefix, logger::trace)
    return this
}

inline fun <T, F : () -> T> F.logTrace(prefix: String = ""): F {
    val logger = KotlinLogging.logger {}
    this.log(prefix, logger::trace)
    return this
}

inline fun <T> T.logDebug(prefix: String = ""): T {
    val logger = KotlinLogging.logger {}
    this.log(prefix, logger::debug)
    return this
}

inline fun <T, F : () -> T> F.logDebug(prefix: String = ""): F {
    val logger = KotlinLogging.logger {}
    this.log(prefix, logger::debug)
    return this
}

inline fun <T> T.logInfo(prefix: String = ""): T {
    val logger = KotlinLogging.logger {}
    this.log(prefix, logger::info)
    return this
}

inline fun <T, F : () -> T> F.logInfo(prefix: String = ""): F {
    val logger = KotlinLogging.logger {}
    this.log(prefix, logger::info)
    return this
}

inline fun <T> T.log(prefix: String, level: (() -> Any?) -> Unit) {
    if (prefix.isEmpty()) level { this }
    else level { "$prefix $this" }
}

inline fun <T, F : () -> T> F.log(prefix: String, level: (() -> Any?) -> Unit) {
    if (prefix.isEmpty()) level { this() }
    else level { "$prefix ${this()}" }
}

fun String.formatPretty(indentSize: Int = 2, startingIndent: Int = 0): String {
    var result = "\n"
    var remaining = this to startingIndent
    while (remaining.first.isNotEmpty()) {
        val (remainingString, currentIndent) = remaining
        val nextIndent = " ".repeat(currentIndent + indentSize)
        val indent = " ".repeat(currentIndent)
        val prevIndent = " ".repeat(max(currentIndent - indentSize, 0))
        
        val co = remainingString.split('{', limit = 2)
        if (co.size == 2) {
            val (head, tail) = co
            if (!head.contains(".*[}\\[\\],].*".toRegex())) {
                remaining = tail to (currentIndent + indentSize)
                result += head.trim(' ') + "{" + "\n$nextIndent"
                continue
            }
        }
        val cc = remainingString.split('}', limit = 2)
        if (cc.size == 2) {
            val (head, tail) = cc
            if (!head.contains(".*[{\\[\\],].*".toRegex())) {
                remaining = tail to (currentIndent - indentSize)
                result += head.trim(' ') + "\n$prevIndent" + "}"
                continue
            }
        }
        val so = remainingString.split('[', limit = 2)
        if (so.size == 2) {
            val (head, tail) = so
            if (!head.contains(".*[{}\\],].*".toRegex())) {
                remaining = tail to (currentIndent + indentSize)
                result += head.trim(' ') + "[" + "\n$nextIndent"
                continue
            }
        }
        val sc = remainingString.split(']', limit = 2)
        if (sc.size == 2) {
            val (head, tail) = sc
            if (!head.contains(".*[{}\\[,].*".toRegex())) {
                remaining = tail to (currentIndent - indentSize)
                result += head.trim(' ') + "\n$prevIndent" + "]"
                continue
            }
        }
        val c = remainingString.split(',', limit = 2)
        if (c.size == 2) {
            val (head, tail) = c
            if (!head.contains(".*[{}\\[\\]].*".toRegex())) {
                remaining = tail to (currentIndent)
                result += head.trim(' ') + "," + "\n$indent"
                continue
            }
        }
        result += remainingString.trim(' ')
    }
    return result
} 