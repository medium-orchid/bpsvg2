package bpsvg2.datatypes

import bpsvg2.SVGBuilder
import kotlin.math.*

data class Quat(val r: Double, val i: Double, val j: Double, val k: Double): DataType {

    constructor(v: Vec3): this(0.0, v.x, v.y, v.z)

    companion object {
        val zero = Quat(0.0, 0.0, 0.0, 0.0)
        val unitR = Quat(1.0, 0.0, 0.0, 0.0)
        val unitI = Quat(0.0, 1.0, 0.0, 0.0)
        val unitJ = Quat(0.0, 0.0, 1.0, 0.0)
        val unitK = Quat(0.0, 0.0, 0.0, 1.0)

        fun fromAxisAngle(v: Vec3, angle: Angle): Quat {
            val r = angle / 2
            val s = r.sin()
            val w = v.normalized()
            return Quat(r.cos(), s * w.x, s * w.y, s * w.z)
        }
    }

    fun approximatelyEquals(other: Quat): Boolean {
        return approx(r, other.r) && approx(i, other.i) && approx(j, other.j) && approx(k, other.k)
    }

    operator fun times(other: Quat): Quat {
        return Quat(
            r * other.r - i * other.i - j * other.j - k * other.k,
            r * other.i + i * other.r + j * other.k - k * other.j,
            r * other.j - i * other.k + j * other.r + k * other.i,
            r * other.k + i * other.j - j * other.i + k * other.r,
        )
    }

    operator fun times(other: Vec3): Vec3 {
        return (this * Quat(other) * reciprocal()).vectorPart().u(other.unit)
    }

    operator fun times(other: Double): Quat {
        return Quat(other * r, other * i, other * j, other * k)
    }

    operator fun plus(other: Quat): Quat {
        return Quat(r + other.r, i + other.i, j + other.j, k + other.k)
    }

    operator fun plus(other: Double): Quat {
        return Quat(r + other, i, j, k)
    }

    operator fun div(other: Double): Quat {
        return Quat(r / other, i / other, j / other, k / other)
    }

    operator fun get(index: Int): Double {
        return when (index) {
            0 -> r
            1 -> i
            2 -> j
            3 -> k
            else -> throw IndexOutOfBoundsException()
        }
    }

    fun normSquared(): Double {
        return r*r + i*i + j*j + k*k
    }

    fun norm(): Double {
        return sqrt(normSquared())
    }

    fun normalized(): Quat {
        return this / norm()
    }

    fun reciprocal(): Quat {
        return conjugate() / normSquared()
    }

    fun polar(): Pair<Double, Quat> {
        // q = ||q|| (cos φ + n sin φ)
        // q^ = cos φ + n sin φ
        // => cos φ = real(q^)
        // => n sin φ = vec(q^)
        //   => n_p sin φ = q^_p
        //     => sin φ = q^_p / n_p
        val t = normalized() // = q^
        val u = imag()
        val uNorm = u.norm()
        if (uNorm == 0.0) {
            return 0.0 to u
        }
        val n = u / uNorm
        val c = t.r
        for (i in 1..<4) {
            if (n[i] != 0.0) {
                val s = t[i] / n[i]
                return atan2(s, c) to n
            }
        }
        return 0.0 to n
    }

    fun exp(): Quat {
        val v = imag()
        val l = v.norm()
        return exp(r) * (cos(l) + v.normalized() * sin(l))
    }

    fun ln(): Quat {
        val n = norm()
        return ln(n) + imag().normalized() * acos(r / n)
    }

    fun geodesicDistance(other: Quat): Double {
        return acos(2 * this.dot(other).pow(2) - 1)
    }

    fun toMat3D(): Mat3D {
        return Mat3D(
            r*r + i*i - j*j - k*k, 2*(i*j - r*k), 2*(i*k + r*j),
            2*(i*j + r*k), r*r - i*i + j*j - k*k, 2*(j*k - r*i),
            2*(i*k - r*j), 2*(j*k + r*i), r*r - i*i - j*j + k*k
        )
    }

    fun vectorPart(): Vec3 {
        return Vec3(i, j, k)
    }

    fun imag(): Quat {
        return Quat(0.0, i, j, k)
    }

    fun conjugate(): Quat {
        return Quat(r, -i, -j, -k)
    }

    fun dot(other: Quat): Double {
        return r * other.r + i * other.i + j * other.j + k * other.k
    }

    fun cross(other: Quat): Quat {
        return Quat(0.0, j * other.k - k * other.j, k * other.i - i * other.k, i * other.j - j * other.i)
    }

    fun pow(x: Double): Quat {
        val r = norm()
        if (r == 0.0) return zero
        val (phi, n) = polar()
        return r.pow(x) * (cos(x * phi) + n * sin(x * phi))
    }

    fun pow(n: Int): Quat {
        return pow(n.toDouble())
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

    override fun toString(): String {
        return "$r + {$i}i + {$j}j {$k}k"
    }
}