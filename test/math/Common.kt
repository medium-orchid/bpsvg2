package math

import bpsvg2.datatypes.*
import bpsvg2.datatypes.math2d.*
import bpsvg2.datatypes.math3d.*
import kotlin.random.Random

fun randomMatrix(): Mat2D {
    return Mat2D(entry(), entry(), entry(), entry(), entry(), entry())
}

fun randomMatrix3(): Mat3D {
    return Mat3D(
        entry(), entry(), entry(), entry(),
        entry(), entry(), entry(), entry(),
        entry(), entry(), entry(), entry(),
        entry(), entry(), entry(), entry(),
    )
}

fun entry(): Double {
    return Random.nextDouble(-2.0, 2.0)
}

fun assert(value: Boolean) {
    if (!value) throw Exception()
}

fun randomUnitVec3(): Vec3 {
    return Vec3(entry(), entry(), entry()).normalized()
}

fun randomAngle(units: AngleUnits = AngleUnits.RAD): Angle {
    return Angle(Random.nextDouble(0.0, units.turn))
}

fun randomUnitQuaternion(): Quat {
    return Quat(entry(), entry(), entry(), entry()).normalized()
}