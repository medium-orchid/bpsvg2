import datatypes.DataType
import java.text.DecimalFormat

class SVGBuilder {
    var indent = "  "
    var indentPath = false
    var newLines = true
    var decimalPattern: String = "0.##########"
        set(value) {
            formatter = DecimalFormat(value)
        }
    private var formatter = DecimalFormat(decimalPattern)
    private val builder = StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    private var indentLevel = 0

    private fun putFormatted(x: Double) {
        builder.append(formatter.format(x))
    }

    fun newline() {
        if (newLines) builder.append("\n")
        builder.append(indent.repeat(indentLevel))
    }

    fun indent() {
        indentLevel += 1
    }

    fun unindent() {
        indentLevel -= 1
    }

    fun append(value: Any) {
        when (value) {
            is Double -> putFormatted(value)
            is DataType -> value.put(this)
            else -> builder.append(value)
        }
    }

    fun withComma(value: Any) {
        append(value)
        builder.append(',')
    }

    fun withSpaceBefore(value: Any) {
        builder.append(' ')
        append(value)
    }

    override fun toString(): String {
        return builder.toString()
    }
}