package bpsvg2.math.d2

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.*
import bpsvg2.eat.OutputMode
import kotlin.math.*

/**
 * A matrix
 * ( a c x )
 * ( b d y )
 * ( 0 0 1 )
 * representing a 2D transformation
 */
data class Mat2D(
    val vx: Vec2, val vy: Vec2, val vc: Vec2
) : DataType {

    constructor(a: Dimension, b: Dimension, c: Dimension, d: Dimension, x: Dimension, y: Dimension):
            this(Vec2(a, c), Vec2(b, d), Vec2(x, y))

    constructor(a: Double, b: Double, c: Double, d: Double, x: Double, y: Double):
            this(Vec2(a, c), Vec2(b, d), Vec2(x, y))

    val a: Dimension get() = vx.x
    val b: Dimension get() = vy.x
    val c: Dimension get() = vx.y
    val d: Dimension get() = vy.y
    val x: Dimension get() = vc.x
    val y: Dimension get() = vc.y

    companion object {

        fun scale(k: Double): Mat2D {
            return Mat2D(k * Vec2.X, k * Vec2.Y, Vec2.zero)
        }

        val id = scale(1.0)

        fun offset(x: Dimension, y: Dimension): Mat2D {
            return Mat2D(Vec2.X, Vec2.Y, Vec2(x, y))
        }

        fun offset(v: Vec2): Mat2D {
            return Mat2D(Vec2.X, Vec2.Y, v)
        }

        fun rotate(angle: Angle): Mat2D {
            val c = angle.cos()
            val s = angle.sin()
            return Mat2D(c, s, -s, c, 0.0, 0.0)
        }

        fun reflect(angle: Angle): Mat2D {
            val r = 2.0 * angle
            val c = r.cos()
            val s = r.sin()
            return Mat2D(c, s, s, -c, 0.0, 0.0)
        }
    }

    fun approximatelyEquals(other: Mat2D): Boolean {
        return approx(a, other.a) && approx(b, other.b) && approx(c, other.c)
                && approx(d, other.d) && approx(x, other.x) && approx(y, other.y)
    }

    fun hasOffset(): Boolean {
        return vc.approx(Vec2.zero)
    }

    operator fun times(other: Mat2D): Mat2D {
        return Mat2D(
            this.a * other.a + this.c * other.b,
            this.b * other.a + this.d * other.b,
            this.a * other.c + this.c * other.d,
            this.b * other.c + this.d * other.d,
            this.a * other.x + this.c * other.y + this.x,
            this.b * other.x + this.d * other.y + this.y,
        )
    }

    operator fun times(other: Vec2): Vec2 {
        return Vec2(vx.dot(other), vy.dot(other)) + vc
    }

    operator fun plus(other: Vec2): Mat2D {
        return Mat2D(vx, vy, vc + other)
    }

    fun det(): Dimension {
        return a * d - b * c
    }

    fun scale(k: Double): Mat2D {
        return Mat2D(a * k, b * k, c * k, d * k, x * k, y * k)
    }

    fun scale(k: Dimension): Mat2D {
        return Mat2D(a * k, b * k, c * k, d * k, x * k, y * k)
    }

    fun isOrthogonal(): Boolean {
        val k = det()
        return approx(a * a + c * c, k)
                && approx(b * b + d * d, k)
                && approx(a * b + c * d, 0.0.d)
    }

    fun toOrtho(): Trans2D {
        if (isOrthogonal()) {
            val k = det()
            val r = Angle.atan2(b, a)
            return Trans2D(k, r, Vec2(x, y))
        } else {
            throw IllegalArgumentException("Matrix is not orthogonal")
        }
    }

    override fun toString(): String {
        return "Mat2D($a $c $x)($b $d $y)"
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        builder.append("matrix(").join(mode, a, b, c, d, x, y).append(")")
    }

    fun inverse(): Mat2D {
        val det = try {
            det()
        } catch (e: Exception) {
            throw IllegalArgumentException("matrix is not invertible")
        }
        return Mat2D(d, -b, -c, a, c * y - d * x, b * x - a * y).scale(one / det)
    }

    fun pow(n: Int): Mat2D {
        if (n == 0) {
            return id
        } else if (n < 0) {
            return inverse().pow(-n)
        } else if (n == 1) {
            return this
        }
        if (approx(b.value, 0.0) && approx(c.value, 0.0)) {
            if (approx(b.value, 0.0) && approx(d.value, 0.0)) {
                return Mat2D.offset(n.toDouble() * x, n.toDouble() * y)
            } else if (vc.approx(Vec2.zero)) {
                return Mat2D(a.pow(n), zero, zero, d.pow(n), zero, zero)
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