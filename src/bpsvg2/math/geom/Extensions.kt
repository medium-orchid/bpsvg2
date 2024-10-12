package bpsvg2.math.geom

import bpsvg2.SVGElement
import bpsvg2.math.d3.*
import bpsvg2.math.d2.*
import bpsvg2.math.*

typealias StateOperation = (LSystem.State) -> Unit
typealias SVGStateOperation = SVGElement.(LSystem.State) -> Unit

fun Surface<Vec2>.determinant(u: Double, v: Double): Dimension {
    return uDerivative(u, v).cross(vDerivative(u, v))
}

fun Polygon<Vec3>.project(projection: Proj32): Polygon<Vec2> {
    return Polygon(vertices.map { v -> projection * v })
}

fun Polygon<Vec3>.normal(): Vec3 {
    var nv = Vec3.zero
    val n = vertices.size
    for (i in vertices.indices) {
        nv += vertices[i].cross(vertices[(i + 1) % n])
    }
    return nv.normalized()
}

fun Polygon<Vec2>.signedArea(): Dimension {
    val n = vertices.size
    var area = zero
    for (i in vertices.indices) {
        area += vertices[i].cross(vertices[(i + 1) % n])
    }
    return area
}

fun Polygon<Vec2>.windingOrder(): Double {
    return signedArea().sign()
}