package bpsvg2.datatypes

import bpsvg2.SVGBuilder
import kotlin.math.*

data class Angle(val value: Double, val unit: AngleUnits = AngleUnits.RAD): DataType {

    companion object {
        val id = Angle(0.0)

        fun atan2(y: Double, x: Double): Angle {
            return Angle(kotlin.math.atan2(y, x))
        }
    }

    fun approximatelyEquals(other: Angle): Boolean {
        return approx(sin(), other.sin()) && approx(cos(), other.cos())
    }

    operator fun plus(other: Angle): Angle {
        return Angle(value + other.toValue(unit), unit)
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

    fun toOrtho(): Ortho2D {
        return Ortho2D(1.0, this, Vec2.zero)
    }

    fun sin(): Double {
        return sin(toValue(AngleUnits.RAD))
    }

    fun cos(): Double {
        return cos(toValue(AngleUnits.RAD))
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

    override fun put(builder: SVGBuilder) {
        builder.append(toValue(AngleUnits.DEG))
    }

    override fun put(builder: SVGBuilder, cssMode: Boolean) {
        if (cssMode) {
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
            put(builder)
        }
    }
}