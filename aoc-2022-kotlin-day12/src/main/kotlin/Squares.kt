class Square private constructor(
    val pos: Pair<Int, Int>,
    private val elevationChar: Char,
) {
    lateinit var grid: Grid
        private set


    val elevation by lazy { elevation(elevationChar) }

    val isStart by lazy { isStart(elevationChar) }
    val isEnd by lazy { isEnd(elevationChar) }
    
    val left by lazy { grid[pos.first].getOrNull(pos.second - 1) }
    val right by lazy { grid[pos.first].getOrNull(pos.second + 1) }
    val up by lazy { grid.getOrNull(pos.first - 1)?.get(pos.second) }
    val down by lazy { grid.getOrNull(pos.first + 1)?.get(pos.second) }

    val canMoveLeft by lazy { canMoveFromTo(this, left) }
    val canMoveRight by lazy { canMoveFromTo(this, right) }
    val canMoveUp by lazy { canMoveFromTo(this, up) }
    val canMoveDown by lazy { canMoveFromTo(this, down) }

    companion object {
        fun isStart(char: Char): Boolean {
            return char == 'S'
        }

        fun isEnd(char: Char): Boolean {
            return char == 'E'
        }

        fun elevation(char: Char): Int {
            if (char == 'S') return 0
            if (char == 'E') return 25
            return char - 'a'
        }

        fun canMoveFromTo(from: Square, to: Square?): Boolean {
            return to is Square && from.elevation + 1 >= to.elevation
        }
    }

    override fun toString(): String {
        return "{elevation: $elevation, row: ${pos.first}, col: ${pos.second}, is start: $isStart, is end: $isEnd, can move left: $canMoveLeft, can move right: $canMoveRight, can move up: $canMoveUp, can move down: $canMoveDown}"
    }
    
    class Grid private constructor(private val squares: List<List<Square>>) : List<List<Square>> by squares {
        lateinit var start: Square
            private set
        lateinit var end: Square
            private set


        init {
            squares.forEach {
                it.forEach { square ->
                    square.grid = this
                    if (square.isStart) start = square
                    if (square.isEnd) end = square
                }
            }
        }

        companion object {
            operator fun invoke(charGrid: List<List<Char>>): Grid = Grid(toSquaresGrid(charGrid))

            private fun toSquaresGrid(charGrid: List<List<Char>>) =
                charGrid.mapIndexed { row, charLine ->
                    charLine.mapIndexed { col, char ->
                        Square(Pair(row, col), char)
                    }
                }
        }

        override fun toString(): String {
            return "{Start: $start, end: $end, grid: $squares}"
        }
    }
}