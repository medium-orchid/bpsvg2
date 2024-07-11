package bpsvg2.math

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.eat.OutputMode
import bpsvg2.math.d2.Vec2

data class Dimension(val value: Double, val unit: U) : DataType, Vector<Dimension> {

    companion object {
        fun niceName(unit: U): String {
            return when (unit) {
                U.HZ -> "Hz"
                U.KHZ -> "kHz"
                U.PERCENT -> "%"
                else -> unit.name.lowercase()
            }
        }

        val converter = UnitConverter()
    }

    fun convertValue(unit: U): Double {
        return converter.convertValue(this, unit)
    }

    fun convert( unit: U): Dimension {
        return converter.convert(this, unit)
    }

    operator fun unaryMinus(): Dimension {
        return Dimension(-value, unit)
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        builder.append(value)
        builder.append(niceName(unit))
    }

    override operator fun plus(other: Dimension): Dimension {
        return if (unit == U.UNITLESS) {
            Dimension(converter.convertValue(this, other.unit) + other.value, other.unit)
        } else {
            Dimension(this.value + converter.convertValue(other, unit), unit)
        }
    }

    override operator fun minus(other: Dimension): Dimension {
        return if (unit == U.UNITLESS) {
            Dimension(converter.convertValue(this, other.unit) - other.value, other.unit)
        } else {
            Dimension(this.value - converter.convertValue(other, unit), unit)
        }
    }

    override operator fun times(other: Double): Dimension {
        return Dimension(other * value, unit)
    }

    operator fun times(other: Dimension): Dimension {
        if (unit == U.UNITLESS) return other * value
        if (other.unit == U.UNITLESS) return this * other.value
        throw IllegalArgumentException("cannot multiply two dimension with units")
    }

    override operator fun div(other: Double): Dimension {
        return Dimension(value / other, unit)
    }

    operator fun div(other: Dimension): Double {
        return this.convertValue(other.unit) / other.value
    }

    override fun toString(): String {
        return "$value${niceName(unit)}"
    }

    fun toVec2(): Vec2 {
        return Vec2(this, this)
    }
}