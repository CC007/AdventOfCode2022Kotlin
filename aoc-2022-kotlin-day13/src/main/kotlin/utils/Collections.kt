package utils

fun <E> MutableList<E>.pop(): E = this.removeLast()
fun <E> MutableList<E>.peek(): E = this.last()
fun <E> MutableList<E>.push(e: E): Boolean = this.add(e)
fun <E> MutableList<E>.enqueue(): E = this.removeFirst()
fun <E> MutableList<E>.dequeue(e: E): Boolean = this.add(e)