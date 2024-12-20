package bpsvg2.math.d2

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.eat.OutputMode
import bpsvg2.math.*
import kotlin.math.*
import kotlin.random.Random

data class Angle(val value: Double, val unit: AngleUnits = AngleUnits.RAD) : DataType {

    companion object {
        val id = Angle(0.0)

        fun atan2(y: Double, x: Double): Angle {
            return Angle(kotlin.math.atan2(y, x))
        }

        fun atan2(y: Dimension, x: Dimension): Angle {
            val (yt, xt) = Dimension.toCommon(x, y)
            return Angle(kotlin.math.atan2(yt.value, xt.value))
        }

        fun random(): Angle {
            return Angle(Random.nextDouble(), AngleUnits.TURNS)
        }
    }

    fun approximatelyEquals(other: Angle): Boolean {
        return approx(sin(), other.sin()) && approx(cos(), other.cos())
    }

    operator fun plus(other: Angle): Angle {
        return Angle(value + other.toValue(unit), unit)
    }

    operator fun minus(other: Angle): Angle {
        return Angle(value - other.toValue(unit), unit)
    }

    operator fun times(other: Vec2): Vec2 {
        val s = sin()
        val c = cos()
        return Vec2(c * other.x - s * other.y, s * other.x + c * other.y)
    }

    operator fun unaryMinus(): Angle {
        return Angle(-value, unit)
    }

    fun mod(): Angle {
        return Angle(value.mod(unit.turn), unit)
    }

    fun toValue(conversion: AngleUnits): Double {
        return value * conversion.turn / unit.turn
    }

    fun to(conversion: AngleUnits): Angle {
        return Angle(toValue(conversion), conversion)
    }

    fun toMat2D(): Mat2D {
        return Mat2D.rotate(this)
    }

    fun toTrans(): Trans2D {
        return Trans2D(1.0, this, Vec2.zero)
    }

    fun sin(): Double {
        return sin(toValue(AngleUnits.RAD))
    }

    fun cos(): Double {
        return cos(toValue(AngleUnits.RAD))
    }

    fun unitVector(): Vec2 {
        return Vec2(cos(), sin())
    }

    fun radius(r: Dimension): Vec2 {
        return Vec2(cos() * r, sin() * r)
    }

    fun radius(r: Double): Vec2 {
        return Vec2(cos() * r, sin() * r)
    }

    operator fun times(other: Double): Angle {
        return Angle(value * other, unit)
    }

    operator fun div(other: Double): Angle {
        return Angle(value / other, unit)
    }

    operator fun div(other: Int): Angle {
        return Angle(value / other.toDouble(), unit)
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        if (mode == OutputMode.CSS) {
            val r = toValue(AngleUnits.RAD)
            val d = toValue(AngleUnits.DEG)
            val t = toValue(AngleUnits.TURNS)
            val g = toValue(AngleUnits.GRAD)
            val lr = builder.getFormattedLength(r)
            val ld = builder.getFormattedLength(d)
            val lt = builder.getFormattedLength(t)
            val lg = builder.getFormattedLength(g)
            val l = arrayOf(lr, ld, lt, lg).min()
            when (l) {
                lt -> builder.append(t).append(AngleUnits.TURNS.str)
                ld -> builder.append(d).append(AngleUnits.DEG.str)
                lg -> builder.append(g).append(AngleUnits.GRAD.str)
                lr -> builder.append(r).append(AngleUnits.RAD.str)
            }
        } else {
            builder.append(toValue(AngleUnits.DEG))
        }
    }
}