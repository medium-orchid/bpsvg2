package bpsvg2.datatypes

import bpsvg2.SVGBuilder
import kotlin.math.*

data class Quaternion(val r: Double, val i: Double, val j: Double, val k: Double): DataType {

    companion object {
        fun fromAxisAngle(v: Vec3, angle: Angle): Quaternion {
            val r = angle / 2
            val s = r.sin()
            val w = v.normalized()
            return Quaternion(r.cos(), s * w.x, s * w.y, s * w.z)
        }
    }

    operator fun times(other: Quaternion): Quaternion {
        return Quaternion(
            r * other.r - i * other.i - j * other.j - k * other.k,
            r * other.i + i * other.r + j * other.k - k * other.j,
            r * other.j - i * other.k + j * other.r + k * other.i,
            r * other.k + i * other.j - j * other.i + k * other.r,
        )
    }

    fun normSquared(): Double {
        return r*r + i*i + j*j + k*k
    }

    fun norm(): Double {
        return sqrt(normSquared())
    }

    fun toMat3D(): Mat3D {
        val s = 2 * normSquared().pow(-2)
        return Mat3D(
            1 - s * (j*j - k*k), s * (i*j - k*r), s * (i*k + j*r),
            s * (i*j + k*r), 1 - s * (i*i + k*k), s * (j*k - i*r),
            s * (i*k - j*r), s * (j*k + i*r), 1 - s * (i*i + j*j),
        )
    }

    fun vectorPart(): Vec3 {
        return Vec3(i, j, k)
    }

    override fun put(builder: SVGBuilder) {
        val w = vectorPart()
        val n = w.norm()
        val t = 2 * Angle.atan2(n, r)
        if (n == 0.0) {
            builder.append("rotate3d(0)")
        } else {
            builder.append("rotate3d(").join(w, t).append(")")
        }
    }
}