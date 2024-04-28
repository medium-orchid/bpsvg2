package bpsvg2.datatypes

import kotlin.math.PI

enum class AngleUnits(val str: String, val turn: Double) {
    RAD("rad", 2 * PI), DEG("deg", 360.0), TURNS("turns", 1.0), GRAD("grad", 400.0);

    fun valueOf(angle: Angle): Double {
        return angle.toValue(this)
    }
}