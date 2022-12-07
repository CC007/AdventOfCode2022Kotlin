sealed class Path(val name: String, val parent: Dir?)
sealed interface Dir : MutableMap<String, SubPath>

class RootDir(private val children: MutableMap<String, SubPath>) : Path("/", null), Dir, MutableMap<String, SubPath> by children{
    override fun toString(): String {
        return "$children"
    }
}
sealed class SubPath(name: String, parent: Dir) : Path(name, parent)

class SubDir(name: String, parent: Dir, private val children: MutableMap<String, SubPath>) : SubPath(name, parent), Dir, MutableMap<String, SubPath> by children {
    override fun toString(): String {
        return "$children"
    }
}
class File(name: String, parent: Dir, val size: Int) : SubPath(name, parent){
    override fun toString(): String {
        return "$size"
    }
}
