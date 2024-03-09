package datatypes

operator fun Double.times(other: Mat2D): Mat2D {
    return Mat2D(
        this * other.a, this * other.b,
        this * other.c, this * other.d,
        this * other.x, this * other.y,
        other.unit,
    )
}

infix fun Double.u(unit: String): Length {
    return Length(this, unit)
}

val Double.percent: Length get() = Length(this, "%")

val Double.em: Length get() = Length(this, "em")

val Double.rem: Length get() = Length(this, "rem")

val Double.px: Length get() = Length(this, "px")

fun Boolean.flag(): Int {
    return if (this) {
        1
    } else {
        0
    }
}