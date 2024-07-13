package bpsvg2.math

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.eat.OutputMode
import bpsvg2.math.d2.Vec2
import kotlin.math.pow

data class Dimension(val value: Double, val unit: CSSUnits) : DataType, Vector<Dimension> {

    companion object {
        fun niceName(unit: CSSUnits): String {
            return when (unit) {
                CSSUnits.HZ -> "Hz"
                CSSUnits.KHZ -> "kHz"
                CSSUnits.PERCENT -> "%"
                else -> unit.name.lowercase()
            }
        }

        val converter = UnitConverter()

        fun toCommon(a: Dimension, b: Dimension): Pair<Dimension, Dimension> {
            return when (a.unit) {
                CSSUnits.UNITLESS -> a.convert(b.unit) to b
                else -> a to b.convert(a.unit)
            }
        }

        fun commonUnit(vararg x: Dimension): CSSUnits {
            for (i in x) {
                if (i.unit != CSSUnits.UNITLESS) return i.unit
            }
            return CSSUnits.UNITLESS
        }
    }

    fun convertValue(unit: CSSUnits): Double {
        return converter.convertValue(this, unit)
    }

    fun convert(unit: CSSUnits): Dimension {
        return converter.convert(this, unit)
    }

    fun pow(n: Int): Dimension {
        return when (n) {
            0 -> 1.0.d
            1 -> this
            else -> if (unit == CSSUnits.UNITLESS) {
                value.pow(n).d
            } else {
                throw IllegalArgumentException("cannot take $n-th power of a dimension with a unit")
            }
        }
    }

    operator fun unaryMinus(): Dimension {
        return Dimension(-value, unit)
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        builder.append(value)
        builder.append(niceName(unit))
    }

    override operator fun plus(other: Dimension): Dimension {
        return if (unit == CSSUnits.UNITLESS) {
            Dimension(converter.convertValue(this, other.unit) + other.value, other.unit)
        } else {
            Dimension(this.value + converter.convertValue(other, unit), unit)
        }
    }

    override operator fun minus(other: Dimension): Dimension {
        return if (unit == CSSUnits.UNITLESS) {
            Dimension(converter.convertValue(this, other.unit) - other.value, other.unit)
        } else {
            Dimension(this.value - converter.convertValue(other, unit), unit)
        }
    }

    override operator fun times(other: Double): Dimension {
        return Dimension(other * value, unit)
    }

    operator fun times(other: Dimension): Dimension {
        if (unit == CSSUnits.UNITLESS) return other * value
        if (other.unit == CSSUnits.UNITLESS) return this * other.value
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