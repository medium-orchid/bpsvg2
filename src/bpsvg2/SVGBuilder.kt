package bpsvg2

import bpsvg2.datatypes.DataType
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

    fun getFormattedLength(x: Double): Int {
        return formatter.format(x).length
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
        val dt = value as? DataType
        if (dt != null) {
            value.put(this)
            return this
        }
        when (value) {
            is Double -> putFormatted(value)
            else -> builder.append(value)
        }
        return this
    }

    fun withComma(value: Any): SVGBuilder {
        append(value)
        builder.append(',')
        return this
    }

    fun join(vararg values: Any): SVGBuilder {
        for (i in 0 ..< values.lastIndex) {
            append(i)
            builder.append(',')
        }
        append(values.last())
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