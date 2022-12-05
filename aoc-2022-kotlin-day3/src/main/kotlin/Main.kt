fun main() {
    val text = object {}.javaClass
        .getResource("inputExample.txt")!!
        .readText()
        .split("\n")

    var prioritySum = 0
    var groupPrioritySum = 0
    var elfNr = 0;
    val elfGroups = arrayListOf("", "", "")
    for (line in text) {
        if (line.isEmpty()) break

        //3a
        val leftCompartment = line.substring(0, line.length / 2).toSet()
        val rightCompartment = line.substring(line.length / 2).toSet()
        val intersection = leftCompartment.intersect(rightCompartment)
        println("i: $intersection")

        val priority: Int = getPriority(intersection.first())
        println("p: $priority")
        prioritySum += priority

        //3b
        elfGroups[elfNr] = line;

        if (elfNr == 2) {
            val asIterable = elfGroups.map(CharSequence::toSet)
            val groupIntersection = asIterable[0].intersect(asIterable[1]).intersect(asIterable[2])
            println("gi: $groupIntersection")
            val groupPriority = getPriority(groupIntersection.first())
            println("gp: $groupPriority")
            groupPrioritySum += groupPriority
        }
        elfNr = (elfNr + 1) % 3;
    }

    println("Priority sum: $prioritySum")
    println("Group priority sum: $groupPrioritySum")
}

private fun getPriority(intersection: Char): Int {
    val priority: Int;
    if (intersection - 'a' >= 0) {
        priority = intersection - 'a' + 1
    } else {
        priority = intersection - 'A' + 27
    }
    return priority
}