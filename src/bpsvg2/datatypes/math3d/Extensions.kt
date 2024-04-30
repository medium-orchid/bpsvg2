package bpsvg2.datatypes.math3d

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

val Double.ortho3D: Ortho3D get() = Ortho3D(this, Quat.id, Vec3.zero)

val Int.ortho3D: Ortho3D get() = Ortho3D(this.toDouble(), Quat.id, Vec3.zero)