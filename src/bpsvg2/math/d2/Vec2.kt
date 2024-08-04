package bpsvg2.math.d2

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.*
import bpsvg2.eat.OutputMode
import kotlin.math.pow
import kotlin.math.sqrt

data class Vec2(val x: Dimension, val y: Dimension) : DataType, Vector<Vec2> {

    constructor(x: Double, y: Double) : this(x.d, y.d)

    companion object {
        val zero = Vec2(0.0, 0.0)
        val center = Vec2(50(CSSUnits.PERCENT), 50(CSSUnits.PERCENT))
        val X = Vec2(1.0, 0.0)
        val Y = Vec2(0.0, 1.0)

        fun randomUnit(): Vec2 {
            return Vec2(randomNormal(), randomNormal()).normalized()
        }
    }

    fun convert(unitX: CSSUnits, unitY: CSSUnits): Vec2 {
        return Vec2(x.convert(unitX), y.convert(unitY))
    }

    fun approx(other: Vec2): Boolean {
        return approx(x.value, other.x.convertValue(x.unit))
                && approx(y.value, other.y.convertValue(y.unit))
    }

    override operator fun plus(other: Vec2): Vec2 {
        return Vec2(this.x + other.x, this.y + other.y)
    }

    override operator fun minus(other: Vec2): Vec2 {
        return Vec2(this.x - other.x, this.y - other.y)
    }

    override fun norm(): Dimension {
        val unit = Dimension.commonUnit(x, y)
        return Dimension(sqrt(x.convertValue(unit).pow(2) + y.convertValue(unit).pow(2)), unit)
    }

    fun normalized(): Vec2 {
        return this / norm()
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        builder.withComma(x)
        builder.append(y)
    }

    override operator fun times(other: Double): Vec2 {
        return Vec2(x * other, y * other)
    }

    override operator fun div(other: Double): Vec2 {
        return Vec2(x / other, y / other)
    }

    operator fun div(other: Dimension): Vec2 {
        return Vec2(x / other, y / other)
    }

    fun dot(other: Vec2): Dimension {
        return x * other.x + y * other.y
    }

    fun cross(other: Vec2): Dimension {
        return x * other.y - y * other.x
    }

    override fun toString(): String {
        return "Vec2($x, $y)"
    }

    fun toTrans(): Trans2D {
        return Trans2D(one, Angle.id, this)
    }
}