package bpsvg2.math

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.eat.OutputMode
import bpsvg2.math.d2.Vec2
import java.lang.IllegalArgumentException
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sign

data class Dimension(val value: Double, val unit: CSSUnits, val exp: Double = 1.0) : DataType, Vector<Dimension> {

    companion object {
        fun niceName(unit: CSSUnits): String {
            return when (unit) {
                CSSUnits.UNITLESS -> ""
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

    fun approxExp(other: Dimension): Boolean {
        if (this.unit == CSSUnits.UNITLESS || other.unit == CSSUnits.UNITLESS) return true
        return approx(exp, other.exp)
    }

    fun convertValue(unit: CSSUnits): Double {
        return converter.convertValue(this, unit)
    }

    fun convert(unit: CSSUnits): Dimension {
        return converter.convert(this, unit)
    }

    fun pow(n: Double): Dimension {
        return if (approx(n, 0.0)) {
            1.0.d
        } else {
            Dimension(value.pow(n), unit, exp * n)
        }
    }

    fun pow(n: Int): Dimension {
        return if (n == 0) {
            1.0.d
        } else {
            Dimension(value.pow(n), unit, exp * n)
        }
    }

    operator fun unaryMinus(): Dimension {
        return Dimension(-value, unit, exp)
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        builder.append(value)
        builder.append(niceName(unit))
    }

    override operator fun plus(other: Dimension): Dimension {
        val (a, b) = toCommon(this, other)
        if (!a.approxExp(b)) {
            throw IllegalArgumentException("$a and $b have differing dimension")
        }
        return Dimension(a.value + b.value, a.unit, a.exp)
    }

    override operator fun minus(other: Dimension): Dimension {
        val (a, b) = toCommon(this, other)
        if (!a.approxExp(b)) {
            throw IllegalArgumentException("$a and $b have differing dimension")
        }
        return Dimension(a.value - b.value, a.unit, a.exp)
    }

    override operator fun times(other: Double): Dimension {
        return Dimension(other * value, unit, exp)
    }

    operator fun times(other: Dimension): Dimension {
        if (approx(this, zero) || approx(other, zero)) return zero
        val (a, b) = toCommon(this, other)
        return Dimension(a.value * b.value, a.unit, a.exp + b.exp)
    }

    override operator fun div(other: Double): Dimension {
        return Dimension(value / other, unit)
    }

    override fun norm(): Dimension {
        return Dimension(value.absoluteValue, unit, exp)
    }

    operator fun div(other: Dimension): Dimension {
        val (a, b) = toCommon(this, other)
        return Dimension(a.value / b.value, a.unit, a.exp - b.exp)
    }

    override fun toString(): String {
        if (approx(exp, 1.0)) {
            return "$value${niceName(unit)}"
        } else {
            return "$value${niceName(unit)}^$exp"
        }
    }

    fun toVec2(): Vec2 {
        return Vec2(this, this)
    }

    fun sign(): Double {
        return value.sign
    }
}