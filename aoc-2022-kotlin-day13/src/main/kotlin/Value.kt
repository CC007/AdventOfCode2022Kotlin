import kotlin.collections.List

sealed interface Value: Comparable<Value> {
}
data class ListValue(val contents: MutableList<Value> = arrayListOf()): Value, MutableList<Value> by contents {
    override fun compareTo(other: Value): Int {
        "Compare $this to $other".logTrace()
        return when (other) {
            is IntegerValue -> compareTo(other.asList())
            is ListValue -> compareLists(other)
        }
    }

    private fun compareLists(other: ListValue): Int {
        for((index, value) in this.withIndex()) {
            val otherValue = other.getOrNull(index) ?: return 1.also { "Right side ran out of items".logTrace() }
            val comparison = value.compareTo(otherValue)
            if (comparison != 0) return comparison
        }
        
        return if (this.size == other.size) 0 else (-1).also{"Left side ran out of items".logTrace()}
    }

    override fun toString(): String {
        return contents.toString()
    }
}

data class IntegerValue(val intVal: Int): Value{
    fun asList(): ListValue {
        return ListValue(arrayListOf(this))
    }
    override fun compareTo(other: Value): Int {
        "Compare $this to $other".logTrace()
        return when (other) {
            is IntegerValue -> (this.intVal - other.intVal).also { comparison -> when {
                comparison > 0 -> "Right side is smaller".logTrace()
                comparison < 0 -> "Left side is smaller".logTrace()
            } }
            is ListValue -> asList().compareTo(other)
        }    
    }

    override fun toString(): String {
        return intVal.toString()
    }
}