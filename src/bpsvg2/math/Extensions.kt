package bpsvg2.math

import kotlin.math.*
import kotlin.random.Random

fun approx(a: Double, b: Double): Boolean {
    return abs(a - b) < EPS
}

fun approx(a: Dimension, b: Dimension): Boolean {
    return try {
        val (c, d) = Dimension.toCommon(a, b)
        return approx(c.value, d.value)
    } catch (e: IllegalArgumentException) {
        false
    }
}

const val EPS: Double = 1E-9

fun randomNormal(): Double {
    return sqrt(-2 * ln(Random.nextDouble())) * cos(2 * PI * Random.nextDouble())
}

operator fun Double.times(other: Dimension): Dimension {
    return other * this
}

operator fun Double.invoke(unit: CSSUnits) = Dimension(this, unit)
operator fun Int.invoke(unit: CSSUnits) = Dimension(this.toDouble(), unit)

val Double.d: Dimension get() = Dimension(this, CSSUnits.UNITLESS)
val Double.pct: Dimension get() = Dimension(this, CSSUnits.PERCENT)
val Double.px: Dimension get() = Dimension(this, CSSUnits.PX)
val zero = 0.0.d
val one = 1.0.d

fun Boolean.flag(): Int {
    return if (this) {
        1
    } else {
        0
    }
}

operator fun <V> Double.times(vector: Vector<V>): V {
    return vector * this
}