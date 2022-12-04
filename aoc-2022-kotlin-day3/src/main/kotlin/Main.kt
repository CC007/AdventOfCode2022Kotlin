fun main() {
    val text = object {}.javaClass
        .getResource("input.txt")!!
        .readText()
        .split("\n")

    // 3a
    var prioritySum = 0
    for (line in text) {
        if (line.isEmpty()) break
        val leftCompartment = line.substring(0, line.length/2).asIterable()
        val rightCompartment = line.substring(line.length/2).asIterable()
        val intersection = leftCompartment.intersect(rightCompartment).first()
        // println(intersection)
        
        val priority: Int;
        if (intersection - 'a' >= 0) {
            priority  = intersection - 'a' + 1
        } else {
            priority = intersection - 'A' + 27
        }
        // println(priority)
        prioritySum += priority
    }
    
    println("Priority sum: $prioritySum")
}