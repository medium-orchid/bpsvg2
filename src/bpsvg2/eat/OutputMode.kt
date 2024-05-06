package bpsvg2.eat

enum class OutputMode {
    XML, CSS, Path, Text;

    fun expectedModeError(actual: OutputMode): String {
        return "Expected mode to be ${this.name} (current mode is ${actual.name})"
    }
}