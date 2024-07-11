package bpsvg2.math

import kotlin.math.*
import kotlin.random.Random

fun approx(a: Double, b: Double): Boolean {
    return abs(a - b) < EPS
}

const val EPS: Double = 1E-9

fun randomNormal(): Double {
    return sqrt(-2 * ln(Random.nextDouble())) * cos(2 * PI * Random.nextDouble())
}

operator fun Double.times(other: Dimension): Dimension {
    return other * this
}

operator fun Double.invoke(unit: U) = Dimension(this, unit)
operator fun Int.invoke(unit: U) = Dimension(this.toDouble(), unit)

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