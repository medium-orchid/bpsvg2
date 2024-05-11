package bpsvg2.math.d2

operator fun Double.times(other: Mat2D): Mat2D {
    return Mat2D(
        this * other.a, this * other.b,
        this * other.c, this * other.d,
        this * other.x, this * other.y,
        other.unit,
    )
}

operator fun Double.times(other: Vec2): Vec2 {
    return other * this
}

operator fun Int.times(other: Vec2): Vec2 {
    return other * this.toDouble()
}

operator fun Double.times(other: Ortho2D): Ortho2D {
    return other * this
}

operator fun Int.times(other: Ortho2D): Ortho2D {
    return other * this.toDouble()
}

operator fun Double.times(other: Angle): Angle {
    return other * this
}

operator fun Int.times(other: Angle): Angle {
    return other * this.toDouble()
}

val Double.ortho2D: Ortho2D get() = Ortho2D(this, Angle.id, Vec2.zero)

val Int.ortho2D: Ortho2D get() = Ortho2D(this.toDouble(), Angle.id, Vec2.zero)

val Double.rad: Angle get() = Angle(this, AngleUnits.RAD)

val Double.deg: Angle get() = Angle(this, AngleUnits.DEG)

val Double.turns: Angle get() = Angle(this, AngleUnits.TURNS)

val Double.grad: Angle get() = Angle(this, AngleUnits.GRAD)

val Int.rad: Angle get() = Angle(this.toDouble(), AngleUnits.RAD)

val Int.deg: Angle get() = Angle(this.toDouble(), AngleUnits.DEG)

val Int.turns: Angle get() = Angle(this.toDouble(), AngleUnits.TURNS)

val Int.grad: Angle get() = Angle(this.toDouble(), AngleUnits.GRAD)