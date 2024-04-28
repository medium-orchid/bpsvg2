package bpsvg2.datatypes

import bpsvg2.SVGBuilder

data class Length(val l: Double, val unit: String? = null): DataType {

    init {
        if (unit == "") throw IllegalArgumentException("Unitless lengths should have null unit")
    }

    operator fun unaryMinus(): Length {
        return Length(-l, unit)
    }

    override fun put(builder: SVGBuilder) {
        builder.append(l)
        builder.append(unit ?: "")
    }

    private fun unitGuard(x: Length) {
        if (unit != x.unit) throw IllegalArgumentException("$this and $x do not have the same unit")
    }

    operator fun plus(other: Length): Length {
        unitGuard(other)
        return Length(l + other.l, unit)
    }

    operator fun minus(other: Length): Length {
        unitGuard(other)
        return Length(l - other.l, unit)
    }

    operator fun times(other: Double): Length {
        return Length(other * l, unit)
    }

    operator fun div(other: Double): Length {
        return Length(l / other, unit)
    }

    override fun toString(): String {
        return "$l${unit ?: ""}"
    }
}