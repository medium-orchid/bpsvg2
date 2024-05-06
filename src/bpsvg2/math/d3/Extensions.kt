package bpsvg2.math.d3

import bpsvg2.math.Length

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

operator fun Double.times(other: Ortho3D): Ortho3D {
    return other * this
}

operator fun Int.times(other: Ortho3D): Ortho3D {
    return other * this.toDouble()
}

operator fun Double.times(other: Mat3D): Mat3D {
    return other * this
}

operator fun Int.times(other: Mat3D): Mat3D {
    return other * this.toDouble()
}

operator fun Length.times(other: Vec3): Vec3 {
    return other * this
}

val Double.ortho3D: Ortho3D get() = Ortho3D(this, Quat.id, Vec3.zero)

val Int.ortho3D: Ortho3D get() = Ortho3D(this.toDouble(), Quat.id, Vec3.zero)