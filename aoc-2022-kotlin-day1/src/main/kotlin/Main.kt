import java.io.File

fun main(args: Array<String>) {
    val text = object {}.javaClass.getResource("inputExample.txt")!!
        .readText()
        .split("\n")
    val elfCalories = arrayListOf<Int>()
    var elfCalory = 0
    for (line in text) {
        if (line.isEmpty()) {
            elfCalories.add(elfCalory)
            elfCalory = 0
            continue
        }
        elfCalory += line.toInt()
    }

    println("Amount of elves: ${elfCalories.size}")
    println("Amount of most calories: ${elfCalories.max()}")
}