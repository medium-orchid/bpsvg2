package bpsvg2.datatypes.math3d

import bpsvg2.OutputBuilder
import bpsvg2.datatypes.*

/**
 * A matrix
 * ( a1 a2 a3 a4 )
 * ( b1 b2 b3 b4 )
 * ( c1 c2 c3 c4 )
 * ( d1 d2 d3 d4 )
 * representing a 3D transformation
 */

data class Mat3D(
    val a1: Double, val b1: Double, val c1: Double, val d1: Double,
    val a2: Double, val b2: Double, val c2: Double, val d2: Double,
    val a3: Double, val b3: Double, val c3: Double, val d3: Double,
    val a4: Double, val b4: Double, val c4: Double, val d4: Double
) : DataType {

    constructor(
        a1: Double, b1: Double, c1: Double,
        a2: Double, b2: Double, c2: Double,
        a3: Double, b3: Double, c3: Double
    ) : this(
        a1, b1, c1, 0.0,
        a2, b2, c2, 0.0,
        a3, b3, c3, 0.0,
        0.0, 0.0, 0.0, 1.0
    )

    companion object {

        fun scale(k: Double): Mat3D {
            return Mat3D(
                k, 0.0, 0.0, 0.0,
                0.0, k, 0.0, 0.0,
                0.0, 0.0, k, 0.0,
                0.0, 0.0, 0.0, 1.0
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
        /*
#Sorry
first_col = "abcd"
second_col = "1234"

def var(i, j):
    return first_col[i] + second_col[j]

def term(i, j):
    return " + ".join([var(i, k) + " * other." + var(k, j) for k in range(4)])

print("return Mat3D(\n" + ",\n".join([term(i, j) for i in range(4) for j in range(4)]) + ")")
         */
        return Mat3D(
            a1 * other.a1 + a2 * other.b1 + a3 * other.c1 + a4 * other.d1,
            a1 * other.a2 + a2 * other.b2 + a3 * other.c2 + a4 * other.d2,
            a1 * other.a3 + a2 * other.b3 + a3 * other.c3 + a4 * other.d3,
            a1 * other.a4 + a2 * other.b4 + a3 * other.c4 + a4 * other.d4,
            b1 * other.a1 + b2 * other.b1 + b3 * other.c1 + b4 * other.d1,
            b1 * other.a2 + b2 * other.b2 + b3 * other.c2 + b4 * other.d2,
            b1 * other.a3 + b2 * other.b3 + b3 * other.c3 + b4 * other.d3,
            b1 * other.a4 + b2 * other.b4 + b3 * other.c4 + b4 * other.d4,
            c1 * other.a1 + c2 * other.b1 + c3 * other.c1 + c4 * other.d1,
            c1 * other.a2 + c2 * other.b2 + c3 * other.c2 + c4 * other.d2,
            c1 * other.a3 + c2 * other.b3 + c3 * other.c3 + c4 * other.d3,
            c1 * other.a4 + c2 * other.b4 + c3 * other.c4 + c4 * other.d4,
            d1 * other.a1 + d2 * other.b1 + d3 * other.c1 + d4 * other.d1,
            d1 * other.a2 + d2 * other.b2 + d3 * other.c2 + d4 * other.d2,
            d1 * other.a3 + d2 * other.b3 + d3 * other.c3 + d4 * other.d3,
            d1 * other.a4 + d2 * other.b4 + d3 * other.c4 + d4 * other.d4
        )
    }

    operator fun times(other: Vec3): Vec3 {
        val w = a4 * other.x + b4 * other.y + c4 * other.z + d4
        return Vec3(
            (a1 * other.x + b1 * other.y + c1 * other.z + d1) / w,
            (a2 * other.x + b2 * other.y + c2 * other.z + d2) / w,
            (a3 * other.x + b3 * other.y + c3 * other.z + d3) / w,
        )
    }

    override fun put(builder: OutputBuilder) {
        builder.cssOnly("Mat3D")
        builder.append("matrix3d(")
            .join(a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3, a4, b4, c4, d4)
            .append(")")
    }
}