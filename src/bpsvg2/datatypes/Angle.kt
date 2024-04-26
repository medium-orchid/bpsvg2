package bpsvg2.datatypes

import bpsvg2.SVGBuilder
import kotlin.math.*

data class Angle(val value: Double, val unit: Units = Units.RAD): DataType {

    enum class Units(val str: String) {
        RAD("rad"), DEG("deg"), TURNS("turns"), GRAD("grad")
    }

    companion object {

        fun radToDeg(rad: Double): Double {
            return rad * 180 / PI
        }

        fun degToRad(deg: Double): Double {
            return deg * PI / 180
        }

        fun radToTurns(rad: Double): Double {
            return rad / (2 * PI)
        }

        fun turnsToRad(turns: Double): Double {
            return 2 * PI * turns
        }

        fun radToGrad(rad: Double): Double {
            return rad * 200 / PI
        }

        fun gradToRad(grad: Double): Double {
            return grad * PI / 200
        }

        fun atan2(y: Double, x: Double): Angle {
            return Angle(kotlin.math.atan2(y, x))
        }
    }

    val radians: Double get() {
        return when (unit) {
            Units.RAD -> value
            Units.DEG -> degToRad(value)
            Units.TURNS -> turnsToRad(value)
            Units.GRAD -> gradToRad(value)
        }
    }

    fun sin(): Double {
        return sin(radians)
    }

    fun cos(): Double {
        return cos(radians)
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
        val r = radians
        val d = radToDeg(r)
        val t = radToTurns(r)
        val g = radToGrad(r)
        val lr = builder.getFormattedLength(r)
        val ld = builder.getFormattedLength(d)
        val lt = builder.getFormattedLength(t)
        val lg = builder.getFormattedLength(g)
        val l = arrayOf(lr, ld, lt, lg).min()
        when (l) {
            lt -> builder.append(t).append(Units.TURNS.str)
            ld -> builder.append(d).append(Units.DEG.str)
            lg -> builder.append(g).append(Units.GRAD.str)
            lr -> builder.append(r).append(Units.RAD.str)
        }
    }
}