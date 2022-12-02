enum class Shape(val opponentCode: Char, val myCode: Char, val value: Byte) {
    ROCK('A', 'X', 1),
    PAPER('B', 'Y', 2),
    SCISSORS('C', 'Z', 3);

    fun getScore(other: Shape): Int {
        return (getResult(other) + 1) * 3 + this.value
    }

    fun getResult(other: Shape): Int {
        val shiftedResult = Math.floorMod(this.value - other.value + 1, 3) // 0, 1 or 2
        return shiftedResult - 1 // -1, 0 or 1
    }

    fun getOtherShape(otherResult: Byte): Shape {
        val otherValue = Math.floorMod((this.value - 1 + otherResult), 3) + 1
        return fromValue(otherValue.toByte())
    }

    companion object {
        fun fromOpponentCode(opponentCode: Char): Shape {
            return values().first { with(it.opponentCode, opponentCode::equals) }
        }

        fun fromMyCode(myCode: Char): Shape {
            return values().first { with(it.myCode, myCode::equals) }
        }

        fun fromValue(value: Byte): Shape {
            return values().first { with(it.value, value::equals) }
        }
    }
}