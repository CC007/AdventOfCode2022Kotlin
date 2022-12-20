import kotlin.collections.List

sealed interface Value: Comparable<Value> {
}
data class ListValue(val contents: MutableList<Value> = arrayListOf()): Value, MutableList<Value> by contents {
    override fun compareTo(other: Value): Int {
        return when (other) {
            is IntegerValue -> compareTo(other.asList())
            is ListValue -> compareLists(other)
        }
    }

    private fun compareLists(other: ListValue): Int {
        for((index, value) in this.withIndex()) {
            val otherValue = other.getOrNull(index) ?: return 1
            val comparison = value.compareTo(otherValue)
            if (comparison != 0) return comparison
        }
        return -1
    }
}

data class IntegerValue(val intVal: Int): Value{
    fun asList(): ListValue {
        return ListValue(arrayListOf(this))
    }
    override fun compareTo(other: Value): Int {
        return when (other) {
            is IntegerValue -> this.intVal - other.intVal
            is ListValue -> asList().compareTo(other)
        }    
    }
}