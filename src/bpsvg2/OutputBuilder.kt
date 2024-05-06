package bpsvg2

import bpsvg2.datatypes.DataType
import java.text.DecimalFormat

class OutputBuilder(val indent: String = "  ", val newLine: String = "\n", val indentPath: Boolean = false) {
    var decimalPattern: String = "0.##########"
        set(value) {
            formatter = DecimalFormat(value)
        }
    var cDataStrict = true
    private var cssLevel = 0
    private var formatter = DecimalFormat(decimalPattern)
    private val builder = StringBuilder()
    private var indentLevel = 0

    val cssMode: Boolean get() = cssLevel > 0

    fun svgOpener() {
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
    }

    fun cssOnly(name: String) {
        if (!cssMode) throw IllegalStateException("Cannot put $name outside of CSS")
    }

    private fun putFormatted(x: Double): OutputBuilder {
        builder.append(formatter.format(x))
        return this
    }

    fun getFormattedLength(x: Double): Int {
        return formatter.format(x).length
    }

    fun newline(): OutputBuilder {
        builder.append(newLine)
        builder.append(indent.repeat(indentLevel))
        return this
    }

    fun indent(): OutputBuilder {
        indentLevel += 1
        return this
    }

    fun unindent(): OutputBuilder {
        indentLevel -= 1
        return this
    }

    fun enterCSS() {
        cssLevel++
    }

    fun exitCSS() {
        cssLevel--
    }

    fun append(value: Any): OutputBuilder {
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

    fun withComma(value: Any): OutputBuilder {
        append(value)
        builder.append(',')
        return this
    }

    fun join(vararg values: Any): OutputBuilder {
        for (i in 0..<values.lastIndex) {
            append(values[i])
            builder.append(", ")
        }
        append(values.last())
        return this
    }

    fun withSpaceBefore(value: Any): OutputBuilder {
        builder.append(' ')
        append(value)
        return this
    }

    fun beginCData(): OutputBuilder {
        builder.append("<![CDATA[")
        return this
    }

    fun endCData(): OutputBuilder {
        builder.append("]]>")
        return this
    }

    override fun toString(): String {
        return builder.toString()
    }
}