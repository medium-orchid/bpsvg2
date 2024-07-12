package bpsvg2.math.d2

import bpsvg2.math.*

operator fun Dimension.times(other: Vec2): Vec2 {
    return Vec2(this * other.x, this * other.y)
}

operator fun Dimension.times(other: Mat2D): Mat2D {
    return Mat2D(
        this * other.vx, this * other.vy, this * other.vc
    )
}

operator fun Double.times(other: Angle): Angle {
    return other * this
}

val Double.trans2D: Trans2D get() = Trans2D(this.d, Angle.id, Vec2.zero)

val Double.rad: Angle get() = Angle(this, AngleUnits.RAD)

val Double.deg: Angle get() = Angle(this, AngleUnits.DEG)

val Double.turns: Angle get() = Angle(this, AngleUnits.TURNS)

val Double.grad: Angle get() = Angle(this, AngleUnits.GRAD)

val Int.rad: Angle get() = Angle(this.toDouble(), AngleUnits.RAD)

val Int.deg: Angle get() = Angle(this.toDouble(), AngleUnits.DEG)

val Int.turns: Angle get() = Angle(this.toDouble(), AngleUnits.TURNS)

val Int.grad: Angle get() = Angle(this.toDouble(), AngleUnits.GRAD)