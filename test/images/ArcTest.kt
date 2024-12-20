package images

import bpsvg2.*
import bpsvg2.math.Rect
import bpsvg2.math.d2.*

fun main() {
    SVGElement.root("viewBox" to Rect(20.0, 20.0)) {
        val origin = Vec2(6.0, 10.0)
        val endPoint = Vec2(14.0, 10.0)
        val rx = 6.0
        val ry = 4.0
        val rotation = 10.deg
        path("fill" to "none", "stroke" to "red") {
            moveTo(origin)
            arcTo(rx, ry, rotation, largeArc = true, clockwise = false, endPoint)
        }
        path("fill" to "none", "stroke" to "lime") {
            moveTo(origin)
            arcTo(rx, ry, rotation, largeArc = true, clockwise = true, endPoint)
        }
        path("fill" to "none", "stroke" to "purple") {
            moveTo(origin)
            arcTo(rx, ry, rotation, largeArc = false, clockwise = true, endPoint)
        }
        path("fill" to "none", "stroke" to "pink") {
            moveTo(origin)
            arcTo(rx, ry, rotation, largeArc = false, clockwise = false, endPoint)
        }
    }.saveTo("out/arc_test.svg")
}