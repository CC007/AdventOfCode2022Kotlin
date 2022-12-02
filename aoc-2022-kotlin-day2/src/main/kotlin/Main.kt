import kotlin.math.round

fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .split("\n")

    var totalScore = 0
    for (line in text) {
        if (line.isEmpty()) break
        val codes = line.split(" ")
        val opponentShape = Shape.fromOpponentCode(codes[0].first())
        val myShape = Shape.fromMyCode(codes[1].first())
        val roundScore = myShape.getScore(opponentShape)
        println(roundScore)
        totalScore += roundScore
    }

    println("Total: $totalScore")
}