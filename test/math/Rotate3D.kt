package math

import bpsvg2.math.d3.*
import bpsvg2.math.*
import kotlin.math.*

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
    // Quat and Mat3D product equivalence
    for (i in 0..<100) {
        val p = randomUnitQuaternion()
        val q = randomUnitQuaternion()
        val v = randomUnitVec3()
        assert(((p * q) * v).approximatelyEquals((p.toMat3D() * q.toMat3D()) * v))
    }
    // Quat and Trans3D equivalence
    for (i in 0..<100) {
        val p = randomUnitQuaternion()
        val q = randomUnitQuaternion()
        val v = 1.px * randomUnitVec3()
        val w = 1.px * randomUnitVec3()
        assert((
            q * p * (v + w)
        ).approximatelyEquals(
            (q.toTrans() * (p.toTrans() * w.toTrans())) * v
        ))
    }
    // Trans3D inversion
    for (i in 0..<100) {
        val t = Trans3D(0.25, Quat.randomUnit(), Vec3.randomUnit())
        assert((
            t * t.inverse()
        ).approximatelyEquals(
            Trans3D.id
        ))
        println(t * t.inverse())
    }
    // Mat3D multiplication
    for (i in 0..<100) {
        val m = randomMatrix3()
        val n = randomMatrix3()
        val v = Vec3.randomUnit()
        assert(
            ((n * m) * v)
                .approximatelyEquals(
                    n * (m * v)
                )
        )
        val p = randomMatrix3()
        assert(
            ((n * m) * p)
                .approximatelyEquals(
                    n * (m * p)
                )
        )
    }
    // Polar form
    for (i in 0..<100) {
        val q = randomUnitQuaternion()
        val (t, n) = q.polar()
        val p = cos(t) + n * sin(t)
        assert(q.approximatelyEquals(p))
        val e = (t * n).exp()
        assert(q.approximatelyEquals(e))
    }
    // Powers
    for (i in 0..<100) {
        val q = randomUnitQuaternion()
        assert((q * q * q).approximatelyEquals(q.pow(3)))
    }
}