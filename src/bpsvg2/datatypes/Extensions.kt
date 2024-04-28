package bpsvg2.datatypes

import kotlin.math.abs

fun approx(a: Double, b: Double): Boolean {
    return abs(a - b) < EPS
}

const val EPS: Double = 1E-6

operator fun Double.times(other: Mat2D): Mat2D {
    return Mat2D(
        this * other.a, this * other.b,
        this * other.c, this * other.d,
        this * other.x, this * other.y,
        other.unit,
    )
}

operator fun Double.times(other: Length): Length {
    return other * this
}

operator fun Int.times(other: Length): Length {
    return other * this.toDouble()
}

operator fun Double.times(other: Vec2): Vec2 {
    return other * this
}

operator fun Int.times(other: Vec2): Vec2 {
    return other * this.toDouble()
}

operator fun Double.times(other: Vec3): Vec3 {
    return other * this
}

operator fun Int.times(other: Vec3): Vec3 {
    return other * this.toDouble()
}

operator fun Double.times(other: Quat): Quat {
    return other * this
}

operator fun Int.times(other: Quat): Quat {
    return other * this.toDouble()
}

operator fun Double.times(other: Angle): Angle {
    return other * this
}

operator fun Int.times(other: Angle): Angle {
    return other * this.toDouble()
}

operator fun Double.plus(other: Quat): Quat {
    return other + this
}

operator fun Int.plus(other: Quat): Quat {
    return other + this.toDouble()
}

operator fun Double.times(other: Ortho2D): Ortho2D {
    return other * this
}

operator fun Int.times(other: Ortho2D): Ortho2D {
    return other * this.toDouble()
}

operator fun Double.times(other: Ortho3D): Ortho3D {
    return other * this
}

operator fun Int.times(other: Ortho3D): Ortho3D {
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

val Double.rad: Angle get() = Angle(this, AngleUnits.RAD)

val Double.deg: Angle get() = Angle(this, AngleUnits.DEG)

val Double.turns: Angle get() = Angle(this, AngleUnits.TURNS)

val Double.grad: Angle get() = Angle(this, AngleUnits.GRAD)

val Double.ortho2D: Ortho2D get() = Ortho2D(this, Angle.id, Vec2.zero)

val Double.ortho3D: Ortho3D get() = Ortho3D(this, Quat.unitR, Vec3.zero)

val Int.length: Length get() = Length(this.toDouble())

val Int.percent: Length get() = Length(this.toDouble(), "%")

val Int.em: Length get() = Length(this.toDouble(), "em")

val Int.rem: Length get() = Length(this.toDouble(), "rem")

val Int.px: Length get() = Length(this.toDouble(), "px")

val Int.rad: Angle get() = Angle(this.toDouble(), AngleUnits.RAD)

val Int.deg: Angle get() = Angle(this.toDouble(), AngleUnits.DEG)

val Int.turns: Angle get() = Angle(this.toDouble(), AngleUnits.TURNS)

val Int.grad: Angle get() = Angle(this.toDouble(), AngleUnits.GRAD)

val Int.ortho2D: Ortho2D get() = Ortho2D(this.toDouble(), Angle.id, Vec2.zero)

val Int.ortho3D: Ortho3D get() = Ortho3D(this.toDouble(), Quat.unitR, Vec3.zero)

fun Boolean.flag(): Int {
    return if (this) {
        1
    } else {
        0
    }
}