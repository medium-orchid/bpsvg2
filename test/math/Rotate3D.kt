package math

import bpsvg2.datatypes.*

fun main() {
    // Rodrigues' formula equivalence
    for (i in 0..<100) {
        val k = randomUnitVec3()
        val t = randomAngle()
        val v = randomUnitVec3()
        val quatMat = Quat.fromAxisAngle(k, t) * v
        val rodrigues = v * t.cos() + (k.cross(v)) * t.sin() + k * k.dot(v) * (1 - t.cos())
        assert(quatMat.approximatelyEquals(rodrigues))
    }
    // Quat and Mat3D equivalence
    for (i in 0..<100) {
        val q = randomUnitQuaternion()
        val v = randomUnitVec3()
        assert((q * v).approximatelyEquals(q.toMat3D() * v))
    }
}