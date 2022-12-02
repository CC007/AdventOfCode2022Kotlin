enum class Shape(val opponentCode: Char, val myCode: Char, val value: Byte) {
    ROCK('A', 'X', 1),
    PAPER('B', 'Y', 2),
    SCISSORS('C', 'Z', 3);

    fun getScore(other: Shape): Int {
        return Math.floorMod(this.value - other.value + 1, 3) * 3 + this.value
    }

    companion object {
        fun fromMyCode(myCode: Char): Shape {
            return values().first { shape -> shape.myCode.equals(myCode) }
        }

        fun fromOpponentCode(opponentCode: Char): Shape {
            return values().first { shape -> shape.opponentCode.equals(opponentCode) }
        }
    }
}