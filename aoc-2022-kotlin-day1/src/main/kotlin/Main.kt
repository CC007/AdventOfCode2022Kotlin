import java.util.PriorityQueue

fun main() {
    val text = object {}.javaClass
        .getResource("input.txt")!!
        .readText()
        .lines()
    
    val elfCalories = PriorityQueue<Int> { a, b -> b - a }
    var elfCalorie = 0
    for (line in text) {
        if (line.isEmpty()) {
            elfCalories.add(elfCalorie)
            elfCalorie = 0
            continue
        }
        elfCalorie += line.toInt()
    }

    println("Amount of elves: ${elfCalories.size}")
    val first = elfCalories.poll()
    val second = elfCalories.poll()
    val third = elfCalories.poll()
    println("Amount of most calories: $first")
    println("Amount of 2nd most calories: $second")
    println("Amount of 3rd most calories: $third")
    println("Amount of three most calories: ${first + second + third}")
}