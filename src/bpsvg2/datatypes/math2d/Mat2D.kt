package bpsvg2.datatypes.math2d

import bpsvg2.OutputBuilder
import bpsvg2.datatypes.*
import kotlin.math.*

/**
 * A matrix
 * ( a c x )
 * ( b d y )
 * ( 0 0 1 )
 * representing a 2D transformation
 */
data class Mat2D(
    val a: Double, val b: Double,
    val c: Double, val d: Double,
    val x: Double, val y: Double,
    val unit: String? = null
) : DataType {

    companion object {

        fun scale(k: Double): Mat2D {
            return Mat2D(k, 0.0, 0.0, k, 0.0, 0.0, null)
        }

        val id = scale(1.0)

        fun offset(x: Double, y: Double, unit: String? = null): Mat2D {
            return Mat2D(1.0, 0.0, 0.0, 1.0, x, y, unit)
        }

        fun offset(by: Vec2): Mat2D {
            return offset(by.x, by.y, by.unit)
        }

        fun offset(x: Int, y: Int, unit: String? = null): Mat2D {
            return offset(x.toDouble(), y.toDouble(), unit)
        }

        private fun approx(a: Double, b: Double): Boolean {
            return abs(a - b) < EPS
        }

        private fun offsetUnits(unit: String?): String {
            return if (unit == null) {
                "unitless"
            } else {
                "'${unit}'"
            }
        }

        fun rotate(angle: Angle): Mat2D {
            val c = angle.cos()
            val s = angle.sin()
            return Mat2D(c, s, -s, c, 0.0, 0.0)
        }

        fun reflect(angle: Angle): Mat2D {
            val r = 2 * angle
            val c = r.cos()
            val s = r.sin()
            return Mat2D(c, s, s, -c, 0.0, 0.0)
        }
    }

    fun approximatelyEquals(other: Mat2D): Boolean {
        return approx(a, other.a) && approx(b, other.b) && approx(c, other.c)
                && approx(d, other.d) && approx(x, other.x) && approx(y, other.y)
    }

    private fun vectorGuard(other: Vec2) {
        if (this.hasOffset() && unit != other.unit) {
            throw IllegalArgumentException(
                "Matrix has incompatible offset units" +
                        " (${offsetUnits(this.unit)} and ${offsetUnits(other.unit)})"
            )
        }
    }

    private fun matrixGuard(other: Mat2D) {
        if (this.hasOffset() && other.hasOffset() && this.unit != other.unit) {
            throw IllegalArgumentException(
                "Matrices have incompatible offset units" +
                        " (${offsetUnits(this.unit)} and ${offsetUnits(other.unit)})"
            )
        }
    }

    fun hasOffset(): Boolean {
        return x != 0.0 || y != 0.0
    }

    operator fun times(other: Mat2D): Mat2D {
        matrixGuard(other)
        return Mat2D(
            this.a * other.a + this.c * other.b,
            this.b * other.a + this.d * other.b,
            this.a * other.c + this.c * other.d,
            this.b * other.c + this.d * other.d,
            this.a * other.x + this.c * other.y + this.x,
            this.b * other.x + this.d * other.y + this.y,
            this.unit ?: other.unit
        )
    }

    operator fun times(other: Vec2): Vec2 {
        vectorGuard(other)
        return Vec2(a * other.x + c * other.y + x, b * other.x + d * other.y + y, other.unit)
    }

    operator fun plus(other: Vec2): Mat2D {
        vectorGuard(other)
        return Mat2D(a, b, c, d, x + other.x, y + other.y, other.unit)
    }

    fun det(): Double {
        return a * d - b * c
    }

    fun scale(k: Double): Mat2D {
        return Mat2D(a * k, b * k, c * k, d * k, x * k, y * k)
    }

    fun isOrthogonal(): Boolean {
        val k = det()
        return approx(a * a + c * c, k)
                && approx(b * b + d * d, k)
                && approx(a * b + c * d, 0.0)
    }

    fun toOrtho(): Ortho2D {
        if (isOrthogonal()) {
            val k = det()
            val r = Angle.atan2(b, a)
            return Ortho2D(k, r, Vec2(x, y))
        } else {
            throw IllegalArgumentException("Matrix is not orthogonal")
        }
    }

    override fun toString(): String {
        return "Mat2D($a $c $x)($b $d $y)"
    }

    override fun put(builder: OutputBuilder) {
        builder.append("matrix(").join(a, b, c, d, x, y).append(")")
    }

    fun inverse(): Mat2D {
        val det = try {
            det()
        } catch (e: Exception) {
            throw IllegalArgumentException("matrix is not invertible")
        }
        return Mat2D(d, -b, -c, a, c * y - d * x, b * x - a * y).scale(1 / det)
    }

    fun pow(n: Int): Mat2D {
        if (n == 0) {
            return id
        } else if (n < 0) {
            return inverse().pow(-n)
        } else if (n == 1) {
            return this
        }
        if (b == 0.0 && c == 0.0) {
            if (a == 0.0 && d == 0.0) {
                return Mat2D(0.0, 0.0, 0.0, 0.0, n * x, n * y)
            } else if (x == 0.0 && y == 0.0) {
                return Mat2D(a.pow(n), 0.0, 0.0, d.pow(n), 0.0, 0.0)
            }
        }
        var remaining = n
        var exp = 0
        var out = id
        var current = this
        while (remaining > 0) {
            val powOf2 = 1 shl exp
            if (remaining and powOf2 != 0) {
                out *= current
                remaining -= powOf2
            }
            exp++
            current *= current
        }
        return out
    }
}