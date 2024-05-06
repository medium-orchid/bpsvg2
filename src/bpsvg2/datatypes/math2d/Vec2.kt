package bpsvg2.datatypes.math2d

import bpsvg2.eat.OutputBuilder
import bpsvg2.datatypes.*
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

    fun approximatelyEquals(other: Vec2): Boolean {
        return unit == other.unit && approx(x, other.x) && approx(y, other.y)
    }

    fun u(unit: String? = null): Vec2 {
        return Vec2(x, y, unit)
    }

    private fun guard(other: Vec2) {
        if ((this.approximatelyEquals(zero) && this.unit == null)
            || (other.approximatelyEquals(zero) && other.unit == null)) {
            return
        }
        if (this.unit != other.unit) {
            throw IllegalArgumentException("$this and $other have different units and are not compatible")
        }
    }

    operator fun plus(other: Vec2): Vec2 {
        guard(other)
        return Vec2(this.x + other.x, this.y + other.y, unit ?: other.unit)
    }

    operator fun minus(other: Vec2): Vec2 {
        guard(other)
        return Vec2(this.x - other.x, this.y - other.y, unit ?: other.unit)
    }

    fun normSquared(): Double {
        return x * x + y * y
    }

    fun norm(): Double {
        return sqrt(normSquared())
    }

    fun normalized(): Vec2 {
        return this / norm()
    }

    override fun put(builder: OutputBuilder) {
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

    fun dot(other: Vec2): Double {
        return x * other.x + y * other.y
    }

    fun cross(other: Vec2): Double {
        return x * other.y - y * other.x
    }

    override fun toString(): String {
        return "Vec2($x, $y)${unit ?: ""}"
    }

    fun toOrtho(): Ortho2D {
        return Ortho2D(1.0, Angle.id, this)
    }
}