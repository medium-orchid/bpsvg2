package bpsvg2.math.geom

import bpsvg2.math.Dimension
import bpsvg2.math.d2.Vec2

fun Surface<Vec2>.determinant(u: Double, v: Double): Dimension {
    return uDerivative(u, v).cross(vDerivative(u, v))
}