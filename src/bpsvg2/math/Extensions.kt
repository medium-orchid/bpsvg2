package bpsvg2.math

import kotlin.math.*
import kotlin.random.Random

fun approx(a: Double, b: Double): Boolean {
    return abs(a - b) < EPS
}

const val EPS: Double = 1E-6

fun randomNormal(): Double {
    return sqrt(-2 * ln(Random.nextDouble())) * cos(2 * PI * Random.nextDouble())
}

operator fun Double.times(other: Length): Length {
    return other * this
}

operator fun Int.times(other: Length): Length {
    return other * this.toDouble()
}

infix fun Double.u(unit: String): Length {
    return Length(this, unit)
}

val Double.length: Length get() = Length(this)

val Double.percent: Length get() = Length(this, "%")

val Double.em: Length get() = Length(this, "em")

val Double.rem: Length get() = Length(this, "rem")

val Double.px: Length get() = Length(this, "px")

val Int.length: Length get() = Length(this.toDouble())

val Int.percent: Length get() = Length(this.toDouble(), "%")

val Int.em: Length get() = Length(this.toDouble(), "em")

val Int.rem: Length get() = Length(this.toDouble(), "rem")

val Int.px: Length get() = Length(this.toDouble(), "px")

fun Boolean.flag(): Int {
    return if (this) {
        1
    } else {
        0
    }
}