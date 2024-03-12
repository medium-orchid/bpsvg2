package datatypes

import SVGBuilder
import kotlin.math.*

data class Mat2D(val a: Double, val b: Double,
                 val c: Double, val d: Double,
                 val x: Double, val y: Double,
                 val unit: String? = null
    ): DataType {

    companion object {

        private const val EPS: Double = 1E-6

        fun scale(k: Double): Mat2D {
            return Mat2D(k, 0.0, 0.0, k, 0.0, 0.0, null)
        }

        val id = scale(1.0)

        fun offset(vec2: Vec2): Mat2D {
            return Mat2D(1.0, 0.0, 0.0, 1.0, vec2.x, vec2.y, vec2.unit)
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

        fun rotate(degrees: Double): Mat2D {
            val r = PI * degrees / 180
            val c = cos(r)
            val s = sin(r)
            return Mat2D(c, s, -s, c, 0.0, 0.0)
        }

        fun rotate(degrees: Int): Mat2D {
            return rotate(degrees.toDouble())
        }
    }

    private fun vectorGuard(other: Vec2) {
        if (this.hasOffset() && unit != other.unit) {
            throw IllegalArgumentException("Matrix has incompatible offset units" +
                    " (${offsetUnits(this.unit)} and ${offsetUnits(other.unit)})")
        }
    }

    private fun matrixGuard(other: Mat2D) {
        if (this.hasOffset() && other.hasOffset() && this.unit != other.unit) {
            throw IllegalArgumentException("Matrices have incompatible offset units" +
                    " (${offsetUnits(this.unit)} and ${offsetUnits(other.unit)})")
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

    fun isOrthogonal(): Boolean {
        return approx(a*a + c*c, 1.0)
                && approx(b*b + d*d, 1.0)
                && approx(a*b + c*d, 0.0)
    }

    override fun put(builder: SVGBuilder) {
        if (isOrthogonal()) {
            val k = det()
            val r = atan2(b, a)
            val rd = 180 * r / PI
            var hasOut = false
            if (!approx(x*x + y*y, 0.0)) {
                builder.append("translate(")
                builder.withComma(x)
                builder.append(y)
                builder.append(")")
                hasOut = true
            }
            if (!approx(k, 1.0)) {
                if (hasOut) builder.append(" ")
                builder.append("scale(")
                builder.append(k)
                builder.append(")")
                hasOut = true
            }
            if (!approx(rd, 0.0)) {
                if (hasOut) builder.append(" ")
                builder.append("rotate(")
                builder.append(rd)
                builder.append(")")
            }
        } else {
            builder.append("matrix(")
            builder.withComma(a)
            builder.withComma(b)
            builder.withComma(c)
            builder.withComma(d)
            builder.withComma(x)
            builder.append(y)
            builder.append(")")
        }
    }
}