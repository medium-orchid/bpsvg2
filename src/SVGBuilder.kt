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

    private fun putFormatted(x: Double): SVGBuilder {
        builder.append(formatter.format(x))
        return this
    }

    fun newline(): SVGBuilder {
        if (newLines) builder.append("\n")
        builder.append(indent.repeat(indentLevel))
        return this
    }

    fun indent(): SVGBuilder {
        indentLevel += 1
        return this
    }

    fun unindent(): SVGBuilder {
        indentLevel -= 1
        return this
    }

    fun append(value: Any): SVGBuilder {
        when (value) {
            is Double -> putFormatted(value)
            is DataType -> value.put(this)
            else -> builder.append(value)
        }
        return this
    }

    fun withComma(value: Any): SVGBuilder {
        append(value)
        builder.append(',')
        return this
    }

    fun withSpaceBefore(value: Any): SVGBuilder {
        builder.append(' ')
        append(value)
        return this
    }

    fun beginCData(): SVGBuilder {
        builder.append("<![CDATA[")
        return this
    }

    fun endCData(): SVGBuilder {
        builder.append("]]>")
        return this
    }

    override fun toString(): String {
        return builder.toString()
    }
}