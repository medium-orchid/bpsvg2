package bpsvg2.eat

import bpsvg2.DataType
import java.text.DecimalFormat

class OutputBuilder(val indent: String = "  ", val newLine: String = "\n", val indentPath: Boolean = false) {
    var decimalPattern: String = "0.##########"
        set(value) {
            formatter = DecimalFormat(value)
        }
    var cDataStrict = true
    private var formatter = DecimalFormat(decimalPattern)
    private val builder = StringBuilder()
    private var indentLevel = 0

    private fun putFormatted(x: Double): OutputBuilder {
        builder.append(formatter.format(x))
        return this
    }

    fun getFormattedLength(x: Double): Int {
        return formatter.format(x).length
    }

    fun newline(): OutputBuilder {
        if (builder.isNotEmpty()) builder.append(newLine)
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

    fun append(value: Any, mode: OutputMode = OutputMode.Text): OutputBuilder {
        val dt = value as? DataType
        if (dt != null) {
            value.put(this, mode)
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

    override fun toString(): String {
        return builder.toString()
    }
}