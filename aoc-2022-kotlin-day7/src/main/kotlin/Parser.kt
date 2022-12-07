class Parser(private val text: List<String>) {

    private val rootDir = RootDir(hashMapOf())
    private var currentDir: Dir = rootDir
    private var mode = Mode.COMMAND

    fun parseInput(): RootDir {
        for (line in text.drop(1)) {
            if (line.isEmpty()) break

            when (mode) {
                Mode.COMMAND -> parseCommand(line.substring(2))
                Mode.OUTPUT -> parsePaths(line)
            }
        }
        return rootDir
    }

    private fun parseCommand(line: String) {
        val tmp = currentDir
        when {
            line == "ls" -> {
                mode = Mode.OUTPUT
                currentDir = tmp
            }

            line == "cd .." && tmp is SubDir -> currentDir = tmp.parent!!
            line.startsWith("cd ") -> currentDir = tmp.getValue(line.substring(3)) as Dir
        }

    }

    private fun parsePaths(line: String) {
        if (line.startsWith("$ ")) {
            mode = Mode.COMMAND
            parseCommand(line.substring(2))
            return
        }
        val (sizeOrDir, name) = line.split(" ")
        if (sizeOrDir == "dir") {
            currentDir[name] = SubDir(name, currentDir, hashMapOf())
        } else {
            currentDir[name] = File(name, currentDir, sizeOrDir.toInt())
        }
    }
}