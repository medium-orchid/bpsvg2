package bpsvg2.datatypes

import bpsvg2.SVGBuilder
import kotlin.math.sqrt

data class Vec2(val x: Double, val y: Double, val unit: String? = null) : DataType {

    constructor(x: Int, y: Int, unit: String? = null) : this(x.toDouble(), y.toDouble(), unit)

    constructor(x: Length, y: Length) : this(x.l, y.l, x.unit) {
        if (x.unit != y.unit) throw IllegalArgumentException("$x and $y do not have matching units")
    }

    companion object {
        val zero = Vec2(0, 0)
        val center = Vec2(50.percent, 50.percent)
    }

    init {
        if (unit == "") throw IllegalArgumentException("Unitless vectors should have null unit")
    }

    fun u(unit: String? = null): Vec2 {
        return Vec2(x, y, unit)
    }

    private fun guard(other: Vec2) {
        if (this.unit != other.unit) {
            throw IllegalArgumentException("$this and $other have different units and are not compatible")
        }
    }

    operator fun plus(other: Vec2): Vec2 {
        guard(other)
        return Vec2(this.x + other.x, this.y + other.y, unit)
    }

    operator fun minus(other: Vec2): Vec2 {
        guard(other)
        return Vec2(this.x - other.x, this.y - other.y, unit)
    }

    fun normSquared(): Double {
        return x * x + y * y
    }

    fun norm(): Double {
        return sqrt(normSquared())
    }

    override fun put(builder: SVGBuilder) {
        builder.append(x)
        builder.withComma(unit ?: "")
        builder.append(y)
        builder.append(unit ?: "")
    }

    operator fun times(other: Double): Vec2 {
        return Vec2(x * other, y * other, unit)
    }

    operator fun div(other: Double): Vec2 {
        return Vec2(x / other, y / other, unit)
    }

    operator fun div(other: Int): Vec2 {
        return this / other.toDouble()
    }

    override fun toString(): String {
        return "Vec2($x, $y)$unit"
    }
}