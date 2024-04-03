package images

import bpsvg2.SVG
import bpsvg2.datatypes.Rect
import bpsvg2.datatypes.Vec2

fun main() {
    SVG("viewBox" to Rect(20, 20)) {
        val origin = Vec2(6.0, 10.0)
        val endPoint = Vec2(14.0, 10.0)
        val rx = 6.0
        val ry = 4.0
        val rotation = 10.0
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
    }.to("out/arc_test.svg")
}