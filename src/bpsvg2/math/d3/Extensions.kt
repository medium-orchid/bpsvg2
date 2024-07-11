package bpsvg2.math.d3

import bpsvg2.math.Dimension

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

operator fun Double.plus(other: Quat): Quat {
    return other + this
}

operator fun Int.plus(other: Quat): Quat {
    return other + this.toDouble()
}

operator fun Double.times(other: Trans3D): Trans3D {
    return other * this
}

operator fun Int.times(other: Trans3D): Trans3D {
    return other * this.toDouble()
}

operator fun Double.times(other: Mat3D): Mat3D {
    return other * this
}

operator fun Int.times(other: Mat3D): Mat3D {
    return other * this.toDouble()
}

operator fun Dimension.times(other: Vec3): Vec3 {
    return other * this
}

val Double.trans3D: Trans3D get() = Trans3D(this, Quat.id, Vec3.zero)

val Int.trans3D: Trans3D get() = Trans3D(this.toDouble(), Quat.id, Vec3.zero)