package datatypes

import SVGBuilder
import kotlin.math.sqrt

data class Vec2(val x: Double, val y: Double, val unit: String? = null): DataType {

    constructor(x: Int, y: Int, unit: String? = null): this(x.toDouble(), y.toDouble(), unit)

    companion object {
        val zero = Vec2(0,0)
    }

    init {
        if (unit == "") throw IllegalArgumentException("Unitless vectors should have null unit")
    }

    operator fun plus(other: Vec2): Vec2 {
        if (this.unit != other.unit) {
            throw IllegalArgumentException("Cannot add ($x, $y)$unit to (${other.x}, ${other.y})${other.unit}")
        }
        return Vec2(this.x + other.x, this.y + other.y)
    }

    fun normSquared(): Double {
        return x * x + y + y
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
}