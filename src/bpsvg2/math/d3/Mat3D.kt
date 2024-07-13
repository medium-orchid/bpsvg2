package bpsvg2.math.d3

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.*
import bpsvg2.eat.OutputMode

/**
 * A matrix
 * ( a1 a2 a3 a4 )
 * ( b1 b2 b3 b4 )
 * ( c1 c2 c3 c4 )
 * ( d1 d2 d3 d4 )
 * representing a 3D transformation
 */

data class Mat3D(
    val vx: Vec3, val vy: Vec3, val vz: Vec3, val vw: Vec3, val vc: Vec3, val w: Dimension
) : DataType {

    val a1: Dimension get() = vx.x
    val a2: Dimension get() = vx.y
    val a3: Dimension get() = vx.z
    val a4: Dimension get() = vc.x
    val b1: Dimension get() = vy.x
    val b2: Dimension get() = vy.y
    val b3: Dimension get() = vy.z
    val b4: Dimension get() = vc.y
    val c1: Dimension get() = vz.x
    val c2: Dimension get() = vz.y
    val c3: Dimension get() = vz.z
    val c4: Dimension get() = vc.z
    val d1: Dimension get() = vw.x
    val d2: Dimension get() = vw.y
    val d3: Dimension get() = vw.z
    val d4: Dimension get() = w

    constructor(
        a1: Dimension, b1: Dimension, c1: Dimension, d1: Dimension,
        a2: Dimension, b2: Dimension, c2: Dimension, d2: Dimension,
        a3: Dimension, b3: Dimension, c3: Dimension, d3: Dimension,
        a4: Dimension, b4: Dimension, c4: Dimension, d4: Dimension
    ) : this (
        Vec3(a1, a2, a3), Vec3(b1, b2, b3), Vec3(c1, c2, c3), Vec3(d1, d2, d3),
        Vec3(a4, b4, c4), d4
    )

    constructor(
        a1: Dimension, b1: Dimension, c1: Dimension,
        a2: Dimension, b2: Dimension, c2: Dimension,
        a3: Dimension, b3: Dimension, c3: Dimension
    ) : this(
        a1, b1, c1, zero,
        a2, b2, c2, zero,
        a3, b3, c3, zero,
        zero, zero, zero, one
    )

    constructor(
        a1: Double, b1: Double, c1: Double,
        a2: Double, b2: Double, c2: Double,
        a3: Double, b3: Double, c3: Double
    ) : this(
        a1.d, b1.d, c1.d,
        a2.d, b2.d, c2.d,
        a3.d, b3.d, c3.d
    )

    companion object {

        fun scale(k: Double): Mat3D {
            return Mat3D(
                k, 0.0, 0.0,
                0.0, k, 0.0,
                0.0, 0.0, k,
            )
        }

        val id = scale(1.0)
    }

    fun approximatelyEquals(other: Mat3D): Boolean {
        return approx(a1, other.a1) && approx(b1, other.b1) && approx(c1, other.c1) && approx(d1, other.d1) &&
                approx(a2, other.a2) && approx(b2, other.b2) && approx(c2, other.c2) && approx(d2, other.d2) &&
                approx(a3, other.a3) && approx(b3, other.b3) && approx(c3, other.c3) && approx(d3, other.d3) &&
                approx(a4, other.a4) && approx(b4, other.b4) && approx(c4, other.c4) && approx(d4, other.d4)
    }

    operator fun times(other: Mat3D): Mat3D {
        // Sorry
        /*
#Sorry
first_col = "abcd"
second_col = "1234"

def var(i, j):
    return first_col[i] + second_col[j]

def term(i, j):
    return " + ".join([var(i, k) + " * other." + var(k, j) for k in range(4)])

print("return Mat3D(\n" + ",\n".join([term(i, j) for j in range(4) for i in range(4)]) + ")")
         */
        return Mat3D(
            a1 * other.a1 + a2 * other.b1 + a3 * other.c1 + a4 * other.d1,
            b1 * other.a1 + b2 * other.b1 + b3 * other.c1 + b4 * other.d1,
            c1 * other.a1 + c2 * other.b1 + c3 * other.c1 + c4 * other.d1,
            d1 * other.a1 + d2 * other.b1 + d3 * other.c1 + d4 * other.d1,
            a1 * other.a2 + a2 * other.b2 + a3 * other.c2 + a4 * other.d2,
            b1 * other.a2 + b2 * other.b2 + b3 * other.c2 + b4 * other.d2,
            c1 * other.a2 + c2 * other.b2 + c3 * other.c2 + c4 * other.d2,
            d1 * other.a2 + d2 * other.b2 + d3 * other.c2 + d4 * other.d2,
            a1 * other.a3 + a2 * other.b3 + a3 * other.c3 + a4 * other.d3,
            b1 * other.a3 + b2 * other.b3 + b3 * other.c3 + b4 * other.d3,
            c1 * other.a3 + c2 * other.b3 + c3 * other.c3 + c4 * other.d3,
            d1 * other.a3 + d2 * other.b3 + d3 * other.c3 + d4 * other.d3,
            a1 * other.a4 + a2 * other.b4 + a3 * other.c4 + a4 * other.d4,
            b1 * other.a4 + b2 * other.b4 + b3 * other.c4 + b4 * other.d4,
            c1 * other.a4 + c2 * other.b4 + c3 * other.c4 + c4 * other.d4,
            d1 * other.a4 + d2 * other.b4 + d3 * other.c4 + d4 * other.d4
        )
    }

    operator fun times(other: Vec3): Vec3 {
        val s = vw.dot(other) + w
        return ( Vec3(vx.dot(other), vy.dot(other), vz.dot(other)) + vc ) / s
    }

    operator fun times(other: Double): Mat3D {
        return Mat3D(
            other * vx, other * vy, other * vz, vw, other * vc, w
        )
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        if (mode != OutputMode.CSS) {
            throw IllegalStateException(OutputMode.CSS.expectedModeError(mode))
        }
        builder.append("matrix3d(")
            .join(mode, a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4)
            .append(")")
    }

    override fun toString(): String {
        return "Mat3D\n\t($a1, $b1, $c1, $d1)\n" +
                "\t($a2, $b2, $c2, $d2)\n" +
                "\t($a3, $b3, $c3, $d3)\n" +
                "\t($a4, $b4, $c4, $d4)"
    }
}