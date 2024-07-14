package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.geom.*

fun main() {
    SVGElement.root("viewBox" to Rect.zeroCentered(2.0, 2.0),
        "**" to 300.px) {
        style {
            select("svg")(
                "background-color" to "beige"
            )
            select("path")(
                "fill" to "none"
            )
        }
        val curve = Curve(Curve.QUADRATIC) {_ -> Vec2.randomUnit()}
        path("stroke" to "blue", "stroke-width" to 1.5.pct) {
            fromCurve(curve.split(1.0 / 3, 2.0 / 3))
        }
        path("stroke" to "red", "stroke-width" to 0.5.pct) {
            fromCurve(curve)
        }
    }.saveTo("out/bezier_operations.svg")
}