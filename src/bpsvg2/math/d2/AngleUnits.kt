package bpsvg2.math.d2

import bpsvg2.math.d2.Angle
import kotlin.math.PI

enum class AngleUnits(val str: String, val turn: Double) {
    RAD("rad", 2 * PI), DEG("deg", 360.0), TURNS("turn", 1.0), GRAD("grad", 400.0);

    fun valueOf(angle: Angle): Double {
        return angle.toValue(this)
    }
}