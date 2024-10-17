package bpsvg2.geom

import bpsvg2.SVGElement
import bpsvg2.math.*
import bpsvg2.math.d2.*

typealias StateOperation = (LSystem.State) -> Unit
typealias SVGStateOperation = SVGElement.(LSystem.State) -> Unit

fun Surface<Vec2>.determinant(u: Double, v: Double): Dimension {
    return uDerivative(u, v).cross(vDerivative(u, v))
}