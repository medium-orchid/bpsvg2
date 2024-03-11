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